package com.workflow.general_backend.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.*;
import com.workflow.general_backend.mapper.*;
import com.workflow.general_backend.service.OrdersService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
    ProductMapper productMapper;
    @Resource
    WorkflowMapper workflowMapper;
    @Override
    public List<Orders> findAll() {
        return ordersMapper.findAll();
    }

    @Override
    public List<Orders> findById(String oid) {
        return ordersMapper.findById(oid);
    }

    @Override
    @Transactional
    public CommonResult insert(Orders orders) {
        CommonResult commonResult = new CommonResult();
        String uuid = UUID.randomUUID().toString();
        orders.setOid(uuid);
        float payment=(float) orders.getPayment();
        List<CustomerProfile> cp = customerProfileMapper.findById(orders.getCid());
        Card card=cardMapper.findById(cp.get(0).getCardNum());
        float last=card.getMoney()-payment;
        if(last<0){
            System.out.println("money not enough");
            commonResult.setStatus("Failed");
            commonResult.setMsg("money not enough");
            return commonResult;
        }
        card.setMoney(last);
        cardMapper.update(card);
        System.out.println("set money success");
        try {
            int res = ordersMapper.insert(orders);
            if(res!=1){
                commonResult.setStatus("Failed");
                commonResult.setMsg("backend insert failed");
                return commonResult;
            }
            try {
                RestTemplate template = new RestTemplate();

                // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
                JSONObject json = new JSONObject();
                json.put("pid", orders.getPid());
                json.put("cid", orders.getCid());
                json.put("oid",orders.getOid());
                json.put("payment",orders.getPayment());
                json.put("status",0);
                List<Product> product=productMapper.findById(orders.getPid());
                String fid=product.get(0).getFid();
                System.out.println(fid);
                List<Workflow> workflows=workflowMapper.findById(fid);
                String name=workflows.get(0).getName();
                String url="http://8.141.159.53:5000/api/workflow/"+name;
                // 1、使用postForObject请求接口
                String result = template.postForObject(url, json, String.class);
                System.out.println(result);
                commonResult.setStatus("OK");
                commonResult.setMsg(result);

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
