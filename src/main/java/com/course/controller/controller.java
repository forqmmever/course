package com.course.controller;

import com.course.pojo.PostLog;
import com.course.pojo.PostResult;
import com.course.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world";
    }
    static int DataCnt = 0;

    @PostMapping("/api/metric/put")
    @ResponseBody
    public PostResult ReceiveMonitor(@RequestBody List<PostLog> postDataList) {
        String result = "";
        for (PostLog postData : postDataList) {
            String Rec = service.SavePostLog(postData);
            result += Rec ;
            DataCnt ++;
        }
        Integer code = result == ""?Code.Http_OK:Code.Http_ERR;
        String msg = result == ""?"ok":result;
        return new PostResult(UUID.randomUUID().toString(),msg);
    }
}
