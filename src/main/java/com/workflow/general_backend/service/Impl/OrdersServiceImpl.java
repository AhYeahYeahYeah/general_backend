package com.workflow.general_backend.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.OrdersDto;
import com.workflow.general_backend.dto.OrdersVo;
import com.workflow.general_backend.entity.*;
import com.workflow.general_backend.mapper.*;
import com.workflow.general_backend.service.OrdersService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Resource
    OrdersMapper ordersMapper;
    @Resource
    CustomerProfileMapper customerProfileMapper;
    @Resource
    CardMapper cardMapper;
    @Resource
    RMQPublishService publishService;
    @Override
    public List<OrdersVo> findAll() {
        return ordersMapper.findAll();
    }

    @Override
    public List<Orders> findRecent() {
        return ordersMapper.findRecent();
    }

    @Override
    public List<Orders> findById(String oid) {
        return ordersMapper.findById(oid);
    }

    @Override
    public List<Orders> findByCid(String cid) {
        return ordersMapper.findByCid(cid);
    }

    @Override
    @Transactional
    public CommonResult insert(OrdersDto ordersDto) {
        CommonResult commonResult = new CommonResult();
        String uuid = UUID.randomUUID().toString();
        ordersDto.setOid(uuid);
        //扣款
        float payment=ordersDto.getPayment();
        List<CustomerProfile> cp = customerProfileMapper.findById(ordersDto.getCid());
        Card card=cardMapper.findById(cp.get(0).getCardNum());
        float last=card.getMoney()-payment;
        if(last<0){
            System.out.println("money not enough");
            commonResult.setStatus("Failed");
            commonResult.setMsg("money not enough");
            return commonResult;
        }
        Orders orders = new Orders();
        orders.setOid(uuid);
        orders.setCid(ordersDto.getCid());
        orders.setPid(ordersDto.getPid());
        orders.setPayment(ordersDto.getPayment());
        orders.setOrderDate(ordersDto.getOrderDate());
        orders.setExpireDate(ordersDto.getExpireDate());
        try {
            //插入订单
            orders.setStatus(0);
            int res = ordersMapper.insert(orders);
            if(res!=1){
                commonResult.setStatus("Failed");
                commonResult.setMsg("backend insert failed");
                return commonResult;
            }
            try {
                RmqBody rmqBody=new RmqBody();
                rmqBody.setOrders(ordersDto);
                rmqBody.setNumber(0);
                ObjectMapper objectMapper=new ObjectMapper();
                Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(rmqBody)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                String response=publishService.sendMsg(message);
                System.out.println("消息发送,oid="+ordersDto.getOid());
                if(response.equals("OK")){
                    commonResult.setStatus("OK");
                    commonResult.setMsg(ordersDto.getOid());
                }else {
                    commonResult.setStatus("Failed");
                    commonResult.setMsg(response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                commonResult.setStatus("Failed");
                commonResult.setMsg(e.toString());
            }
            return commonResult;
        }catch (DataAccessException e){
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
    }

    @Override
    public CommonResult update(Orders orders) {
        CommonResult commonResult = new CommonResult();
        try {
            int res = ordersMapper.update(orders);
            if (res == 1) {
                commonResult.setStatus("OK");
            } else {
                commonResult.setStatus("Failed");
            }
            return commonResult;
        }catch (DataAccessException e){
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
    }


}
