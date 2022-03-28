package com.workflow.general_backend.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.WorkflowDto;
import com.workflow.general_backend.entity.Workflow;
import com.workflow.general_backend.mapper.WorkflowMapper;
import com.workflow.general_backend.service.TestRunService;
import com.workflow.general_backend.service.WorkflowService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    @Resource
    WorkflowMapper workflowMapper;
    @Resource
    TestRunService testRunService;

    @Override
    public List<Workflow> findAll() {
        return workflowMapper.findAll();
    }

    @Override
    public List<Workflow> findById(String fid) {
        return workflowMapper.findById(fid);
    }

    @Override
    public CommonResult insert(WorkflowDto workflowDto) {
        CommonResult commonResult = new CommonResult();
        String uuid = UUID.randomUUID().toString();
        workflowDto.setFid(uuid);
        Workflow workflow = new Workflow();
        workflow.setFid(workflowDto.getFid());
        workflow.setName(workflowDto.getName());
        workflow.setDescription(workflowDto.getDescription());
        workflow.setVersion(workflowDto.getVersion());
        workflow.setFlow(workflowDto.getFlow());
        workflow.setAccount(workflowDto.getAccount());
        try {
            RestTemplate template = new RestTemplate();

            // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
//            JSONObject json = JSONObject.parseObject(workflowDto.getMetadataWorkflow());
            JSONObject json =workflowDto.getMetadataWorkflow();
//            TestRunServiceImpl testRunServiceImpl = new TestRunServiceImpl();
            String url = "http://8.141.159.53:5000/api/metadata/workflow/";
            // 1、使用postForObject请求接口
            ResponseEntity<String> result = template.postForEntity(url, json, String.class);
            String status = String.valueOf(result.getStatusCode());

            System.out.println(result + "\n" + status);
            if (status.equals("200 OK"))
                commonResult.setStatus("OK");
            else {
                commonResult.setStatus("Failed");
                commonResult.setMsg("conductor cannot save metaworkflow");
                return commonResult;
            }
            CommonResult commonResult1=testRunService.testRun(String.valueOf(json));
            if(commonResult1.getStatus().equals("Failed")){

                commonResult.setStatus("Failed");
                commonResult.setMsg("test run Failed");
                return commonResult1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
        try {
            int res = workflowMapper.insert(workflow);
            if (res == 1) {
                commonResult.setStatus("OK");
            } else {
                commonResult.setStatus("Failed");
            }
            return commonResult;
        } catch (DataAccessException e) {
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
    }

    @Override
    public CommonResult deleteById(String fid) {
        CommonResult commonResult = new CommonResult();
        int res = workflowMapper.deleteById(fid);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }

    @Override
    public CommonResult update(WorkflowDto workflowDto) {
        CommonResult commonResult = new CommonResult();
        
        try {
            RestTemplate template = new RestTemplate();

            // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
//            JSONObject json = JSONObject.parseObject(workflowDto.getMetadataWorkflow());
            JSONObject json =workflowDto.getMetadataWorkflow();


            String url = "http://8.141.159.53:5000/api/metadata/workflow/";
            // 1、使用postForObject请求接口
            ResponseEntity<String> result = template.postForEntity(url, json, String.class);
            String status = String.valueOf(result.getStatusCode());

            System.out.println(result + "\n" + status);
            if (status.equals("200 OK"))
                commonResult.setStatus("OK");
            else {
                commonResult.setStatus("Failed");
                commonResult.setMsg("conductor cannot save metaworkflow");
                return commonResult;
            }
//            TestRunServiceImpl testRunServiceImpl = new TestRunServiceImpl();
            CommonResult commonResult1=testRunService.testRun(String.valueOf(json));
            if(commonResult1.getStatus().equals("Failed")){

                commonResult.setStatus("Failed");
                commonResult.setMsg("test run Failed");
                return commonResult1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
        try {
            Workflow workflow = new Workflow();
            workflow.setFid(workflowDto.getFid());
            workflow.setName(workflowDto.getName());
            workflow.setDescription(workflowDto.getDescription());
            workflow.setVersion(workflowDto.getVersion());
            workflow.setFlow(workflowDto.getFlow());
            workflow.setAccount(workflowDto.getAccount());
            int res = workflowMapper.update(workflow);
            if (res == 1) {
                commonResult.setStatus("OK");
            } else {
                commonResult.setStatus("Failed");
            }
            return commonResult;
        } catch (DataAccessException e) {
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
    }
}
