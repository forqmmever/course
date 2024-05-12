package com.course.controller;

import com.course.entity.Log;

import com.course.dto.PostResult;
import com.course.entity.MetricConstraint;
import com.course.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world";
    }
//    static int DataCnt = 0;

    @PostMapping("/api/metric/put")
    @ResponseBody
    public PostResult ReceiveMonitor(@RequestBody List<Log> postDataList) throws JsonProcessingException {
        StringBuilder result = new StringBuilder();
        for (Log postData : postDataList) {
            String Rec = service.SavePostLog(postData);
            result.append(Rec);
//            DataCnt ++;
        }
        Integer code = result.toString().equals("") ?Code.Http_OK:Code.Http_ERR;
        String msg = result.toString().equals("") ?"ok": result.toString();
        return new PostResult(UUID.randomUUID().toString(),msg);
    }

    @GetMapping("/api/metric/get")
    @CrossOrigin(origins = " http://192.168.1.111:9528/me")
    public List<Log> GetWarningLogAll(){

        return service.GetWarnigLogAll();
    }

    @GetMapping("/api/constraint/get")
    @ResponseBody
    public List<MetricConstraint> getConstraintAll(){
        return service.GetConstraintAll();
    }
}
