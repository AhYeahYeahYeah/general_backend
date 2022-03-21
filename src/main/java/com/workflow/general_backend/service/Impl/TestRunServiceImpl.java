package com.workflow.general_backend.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.OrdersDto;
import com.workflow.general_backend.entity.*;
import com.workflow.general_backend.mapper.CustomerMapper;
import com.workflow.general_backend.mapper.OrdersMapper;
import com.workflow.general_backend.mapper.ProductMapper;
import com.workflow.general_backend.mapper.WorkflowMapper;
import com.workflow.general_backend.service.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TestRunServiceImpl implements TestRunService {

    @Resource
    CustomerService customerService;
    @Resource
    CustomerProfileService customerProfileService;
    @Resource
    CustomerMapper customerMapper;
    @Resource
    BlacklistService blacklistService;
    @Resource
    WhiteListService whiteListService;
    @Resource
    UserGroupService userGroupService;
    @Resource
    OrdersMapper ordersMapper;
    @Resource
    ProductMapper productMapper;
    @Resource
    WorkflowMapper workflowMapper;
    @Override
    public CommonResult testRun(String jsonStr) throws URISyntaxException {
        CommonResult commonResult=new CommonResult();
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String pid=UUID.randomUUID().toString();
        Product product=new Product();
        product.setPid(pid);
        product.setProductNum("testrun");
        product.setProductName("testrun");
        product.setStorage(10000);
        product.setValidityPeriod("180");
        product.setAnnualRate((float) 0.1);
        product.setMinAmount(100);
        product.setIncreAmount(100);
        product.setSinglePersonLimit(100);
        product.setSingleDayLimit(100);
        product.setRiskLevel(1);
        product.setSettlementMethod("testrun");
        productMapper.insert(product);
        String wid = "";
        Whitelist pastWhitelist=new Whitelist();
        String bid = "";
        Blacklist pastBlacklist=new Blacklist();
        String gid = "";
        UserGroup pastUserGroup=new UserGroup();
        String region = "";

        Customer testCustomer=new Customer();
        testCustomer.setAccount("模拟用户");
        testCustomer.setPassword("test");
        testCustomer.setCname("模拟用户");
        customerService.insert(testCustomer);
        Customer customer=customerMapper.findCustomerByAccount("模拟用户");
        CustomerProfile customerProfile=customerProfileService.findById(customer.getCid()).get(0);
        customerProfile.setCardNum("模拟账户");
        customerProfile.setSid("350602200105152011");
        customerProfile.setPhoneNum("13969455555");
        JSONObject inputTemplate=jsonObject.getJSONObject("inputTemplate");

        if(inputTemplate.get("wid")!=null&&inputTemplate.get("wid")!=""){
            wid=(String) inputTemplate.get("wid");
            pastWhitelist=whiteListService.findById(wid).get(0);
            Whitelist whitelist=new Whitelist();
            whitelist.setWid(pastWhitelist.getWid());
            whitelist.setName(pastWhitelist.getName());
            whitelist.setUsers(pastWhitelist.getUsers().substring(0,pastWhitelist.getUsers().length()-1)+","+customer.getCid()+"]");
            whitelist.setDescription(pastWhitelist.getDescription());
            whiteListService.update(whitelist);
        }
        if(inputTemplate.get("bid")!=null&&inputTemplate.get("bid")!=""){
            bid=(String) inputTemplate.get("bid");
            pastBlacklist=blacklistService.findById(bid).get(0);
            Blacklist blacklist=new Blacklist();
            blacklist.setBid(pastBlacklist.getBid());
            blacklist.setName(pastBlacklist.getName());
            blacklist.setUsers(pastBlacklist.getUsers().substring(0,pastBlacklist.getUsers().length()-1)+","+customer.getCid()+"]");
            blacklist.setDescription(pastBlacklist.getDescription());
            blacklistService.update(blacklist);
        }
        if(inputTemplate.get("gid")!=null&&inputTemplate.get("gid")!=""){
            gid=(String) inputTemplate.get("gid");
            pastUserGroup=userGroupService.findById(gid).get(0);
            UserGroup userGroup=new UserGroup();
            userGroup.setGid(pastUserGroup.getGid());
            userGroup.setName(pastUserGroup.getName());
            userGroup.setUsers(pastUserGroup.getUsers().substring(0,pastUserGroup.getUsers().length()-1)+","+customer.getCid()+"]");
            userGroup.setDescription(pastUserGroup.getDescription());
            userGroupService.update(userGroup);
        }
        if(inputTemplate.get("region")!=null&&inputTemplate.get("region")!="[]"){
            region = inputTemplate.getString("region");
            String[] regionlist=region.replace("[","").replace("]","").replace("\"","").split(",");
            customerProfile.setAddress(regionlist[0]);
        }
        customerProfileService.update(customerProfile);

        String oid=UUID.randomUUID().toString();
        Orders orders=new Orders();
        orders.setOid(oid);
        orders.setPid(pid);
        orders.setCid(customer.getCid());
        orders.setPayment(0);
        orders.setOrderDate(String.valueOf(new Date().getTime()));
        orders.setExpireDate(String.valueOf(new Date().getTime()));
        ordersMapper.insert(orders);

        RestTemplate template = new RestTemplate();
        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        JSONObject json = new JSONObject();
        json.put("pid", orders.getPid());
        json.put("cid", customer.getCid());
        json.put("oid", orders.getOid());
        json.put("phoneNum", customerProfile.getPhoneNum());
        json.put("password", customer.getPassword());
        String name = (String)jsonObject.get("name");
        String url = "http://8.141.159.53:5000/api/workflow/" + name;
        // 1、使用postForObject请求接口
        String resultId = template.postForObject(url, json, String.class);
        System.out.println("resultWorkflowID:-----" + resultId);

        String reUrl="http://8.141.159.53:5000/api/workflow/"+resultId;
        String result = "";
        while (result.equals("")||JSON.parseObject(result).get("status").toString().equals("RUNNING")){
            template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            result = template.getForObject(new URI(reUrl), String.class);
        }
        System.out.println("result:-----" + result);

        if(pastWhitelist.getWid()!=null){
            whiteListService.update(pastWhitelist);
        }
        if(pastBlacklist.getBid()!=null){
            blacklistService.update(pastBlacklist);
        }
        if(pastUserGroup.getGid()!=null){
            userGroupService.update(pastUserGroup);
        }
        productMapper.deleteById(pid);
        ordersMapper.deleteById(oid);
        customerService.deleteById(customer.getCid());
        if(JSON.parseObject(result).get("status").toString().equals("FAILED")){
            commonResult.setStatus("Failed");
            commonResult.setMsg("workflow error");
            String version=jsonObject.getString("version");
            String durl="http://8.141.159.53:5000/api/metadata/workflow/"+name+"/"+version;
            template.delete(durl);

        }else {
            commonResult.setStatus("OK");
            commonResult.setMsg("");
        }
        return commonResult;
    }
}
