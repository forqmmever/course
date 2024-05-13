package com.course.controller;

import com.course.entity.Log;

import com.course.dto.Result;
import com.course.entity.MetricConstraint;
import com.course.entity.TaskSet;
import com.course.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.course.controller.Code.Http_OK;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class controller {
    @Autowired
    private Service service;
//    static int DataCnt = 0

    @PostMapping("/metric/put")
    @ResponseBody
    public Result ReceiveMonitor(@RequestBody List<Log> postDataList) throws JsonProcessingException {
        StringBuilder result = new StringBuilder();
        for (Log postData : postDataList) {
            String Rec = service.SavePostLog(postData);
            result.append(Rec);
        }
        Integer code = result.toString().equals("") ? Http_OK : Code.Http_ERR;
        String msg = result.toString().equals("") ? "ok" : result.toString();
        return new Result(UUID.randomUUID().toString(), msg,Http_OK);
    }

    @GetMapping("/warning/get")
    public List<Log> GetWarningLogAll() {

        return service.GetWarningLogAll();
    }

    @GetMapping("/postlog/get")
    public List<Log> GetPostLogAll() {

        return service.GetPostLogAll();
    }

    @GetMapping("/constraint/get")
    @ResponseBody
    public List<MetricConstraint> getConstraintAll() {
        return service.GetConstraintAll(0);
    }

    @PutMapping("/constraint/update/{metric}")
    public Result UpdateConstraint(@PathVariable String metric, @RequestBody MetricConstraint newData) {
        service.UpdateConstraint(metric,newData);
        return new Result(UUID.randomUUID().toString(), "ok",Http_OK);
    }
    @DeleteMapping("/constraint/delete/{metric}")
    public Result DeleteConstraint(@PathVariable String metric){
        service.DeletConstraint(metric);
        return new Result(UUID.randomUUID().toString(), "ok",Http_OK);
    }

    @PutMapping("/task/put")
    public Result ChangeScheduledTask(@RequestBody TaskSet taskSet){
        service.ChangeTaskSet(taskSet.getInterval(),taskSet.getDate1());
        return new Result(UUID.randomUUID().toString(), "ok",Http_OK);
    }

    @PutMapping("/constraint/new")
    public Result AddConstraint(@RequestBody MetricConstraint metricConstraint){
        System.out.println(metricConstraint);
        service.AddConstraint(metricConstraint);
        return new Result(UUID.randomUUID().toString(), "ok",Http_OK);
    }
}
