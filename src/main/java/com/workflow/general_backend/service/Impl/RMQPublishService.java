package com.workflow.general_backend.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflow.general_backend.entity.RmqBody;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

import static com.workflow.general_backend.config.MQConfiguration.ORDER_EXCHANGE;
import static com.workflow.general_backend.config.MQConfiguration.ORDER_ROUTE_KEY;


@Component
@Service
public class RMQPublishService implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate=new RabbitTemplate();
    //发送订单到rmq
    public String sendMsg(Message message){
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        try {
            rabbitTemplate.convertAndSend(ORDER_EXCHANGE, ORDER_ROUTE_KEY, message, correlationData);
            return "OK";
        }catch (Exception e){
            return e.toString();
        }

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        System.out.println("消息发送,发送ack确认,id="+correlationData.getId());
        if (ack){
            System.out.println("发送成功");
            System.out.println("-----------------------");
        }else {
            System.out.println("发送失败");
            System.out.println("-----------------------");
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        byte[] bytes=returnedMessage.getMessage().getBody();
        ObjectMapper objectMapper=new ObjectMapper();
        String receivedRoutingKey = returnedMessage.getRoutingKey();
        try {
            RmqBody rmqBody=objectMapper.readValue(bytes,RmqBody.class);
            System.out.println("消息丢失, 没有投递成功");
            System.out.println("-----------------------");
            try {
                Thread.sleep(2000L);
                //msg定义为类，类中有限制次数，小于5次进行重传，每次confirm时进行确认
                if(rmqBody.getNumber()<5){
                    rmqBody.setNumber(rmqBody.getNumber()+1);
                    this.rabbitTemplate.convertAndSend(returnedMessage.getExchange(), returnedMessage.getRoutingKey(), returnedMessage.getMessage());
                }else {
                    //对订单进行修改，传给前端失败消息
                    System.out.println("消息失效");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

