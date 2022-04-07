package com.workflow.general_backend.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.workflow.general_backend.dto.OrdersDto;
import com.workflow.general_backend.entity.*;
import com.workflow.general_backend.mapper.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.workflow.general_backend.config.MQConfiguration.DEAD_QUEUE;
import static com.workflow.general_backend.config.MQConfiguration.ORDER_QUEUE;

@Component
@Service
public class RMQReceiveService {
    @Resource
    ProductMapper productMapper;
    @Resource
    WorkflowMapper workflowMapper;
    @Resource
    CustomerProfileMapper customerProfileMapper;
    @Resource
    CardMapper cardMapper;

    // 监听订单队列
    @RabbitListener(queues = ORDER_QUEUE)
    public void orderQueueListener(Message message, Channel channel) throws IOException, InterruptedException {
        byte[] bytes = message.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        RmqBody rmqBody = objectMapper.readValue(bytes, RmqBody.class);
        System.out.println("路由key= [ " + receivedRoutingKey + " ]接收到的消息= [ " + rmqBody.getOrdersDto().getOid() + "  重传次数：" + rmqBody.getNumber() + " ]");
        System.out.println("-----------------------");
        //发送ack给消息队列,收到消息了
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);

        //post Conductor
        OrdersDto orders = rmqBody.getOrdersDto();
        RestTemplate template = new RestTemplate();
        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        JSONObject json = new JSONObject();
        json.put("pid", orders.getPid());
        json.put("cid", orders.getCid());
        json.put("oid", orders.getOid());
        json.put("phoneNum", orders.getPhoneNum());
        json.put("password", orders.getPassword());
        List<Product> product = productMapper.findById(orders.getPid());
        String fid = product.get(0).getFid();
        System.out.println(fid);
        List<Workflow> workflows = workflowMapper.findById(fid);
        String name = workflows.get(0).getName();
        String url = "http://8.141.159.53:5000/api/workflow/" + name;
        // 1、使用postForObject请求接口
        String result = template.postForObject(url, json, String.class);
        System.out.println("resultWorkflowID:-----" + result);
        //通过websocket将workflowid发送到前端
        String resultStatus="";
        while (true){
            ResponseEntity<String> res = template.getForEntity("http://8.141.159.53:5000/api/workflow/"+result, String.class);
            String body = res.getBody();
            JSONObject object = JSON.parseObject(body);
            String status = (String) object.get("status");
            if(!status.equals("RUNNING")){
                System.out.println(body);
                resultStatus=status;
                break;
            }
            Thread.sleep(100);
        }
        if(resultStatus.equals("COMPLETED")){
            //扣款
            float payment=orders.getPayment();
            List<CustomerProfile> cp = customerProfileMapper.findById(orders.getCid());
            Card card=cardMapper.findById(cp.get(0).getCardNum());
            float last=card.getMoney()-payment;
            card.setMoney(last);
            cardMapper.update(card);
            System.out.println("set money success");
        }
        boolean status = false;
        int flag = 0;
        while (true) {
            if (flag > 10) {
                break;
            }
            WebSocketServer webSocketServer = new WebSocketServer();
            if (webSocketServer.getWebSocketMap().containsKey(orders.getOid())) {
                status = true;
                break;
            }
            Thread.sleep(1000);
            flag++;
        }
        if (status) {
            WebSocketServer.sendInfo(result, orders.getOid());
        } else {
            System.out.println("订单：" + orders.getOid() + "连接错误！");
        }


    }

    // 监听死信队列
    @RabbitListener(queues = DEAD_QUEUE)
    public void deadQueueListener(Message message, Channel channel) throws InterruptedException, IOException {
        byte[] bytes = message.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        RmqBody rmqBody = objectMapper.readValue(bytes, RmqBody.class);
        System.out.println("路由key= [ " + receivedRoutingKey + " ]接收到的消息= [ " + rmqBody.getOrdersDto().getOid() + "  重传次数：" + rmqBody.getNumber() + " ]");
        System.out.println("-----------------------");
        Orders orders = rmqBody.getOrdersDto();
        //通过websocket将错误发送到前端
        WebSocketServer.sendInfo("error", orders.getOid());
        //Thread.sleep(5000);
        // 发送ack给消息队列，收到消息了
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
