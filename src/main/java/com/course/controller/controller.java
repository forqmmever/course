package com.course.controller;

import com.course.pojo.PostLog;
import com.course.service.Service;
import com.fasterxml.jackson.databind.JsonNode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class controller {

    private Service service;
    @Autowired
    public controller(Service service){
        this.service = service;
    }
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello world";
    }
    @PostMapping("/api/metric/put")
    @ResponseBody
    public String ReceiveMonitor(@RequestBody List<PostLog> postDataList){
        try {
            for (PostLog postData : postDataList) {
                System.out.println(postData);
                service.SavePostLogPostLog(postData);
            }

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
