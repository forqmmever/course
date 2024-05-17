package com.course.controller;

import com.course.dto.*;
import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import com.course.entity.TaskSet;
import com.course.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.course.dto.Code.Http_ERR;
import static com.course.dto.Code.Http_OK;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class controller {
    @Autowired
    private Service service;

    @PostMapping("/metric/put")
    public ResponseEntity<Result> receiveMonitor(@RequestBody List<Log> postDataList) throws JsonProcessingException {
        StringBuilder result = new StringBuilder();
        for (Log postData : postDataList) {
            String rec = service.SavePostLog(postData);
            result.append(rec);
        }
        Integer code = result.toString().equals("") ? Http_OK : Http_ERR;  // 成功返回 200，不成功返回 201
        String msg = result.toString().equals("") ? "ok" : result.toString();
        Result responseBody = new Result(UUID.randomUUID().toString(), msg);
        return ResponseEntity.status(code).body(responseBody);
    }

    @PostMapping("/metric/query")
    public ResponseEntity<LogResult> QueryLogrData(@RequestBody Request request) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date StartDate = dateFormat.parse(request.getStart());
        int StartTimestamp = (int) (StartDate.getTime() / 1000);
        Date EndDate = dateFormat.parse(request.getEnd());
        int EndTimestamp = (int) (EndDate.getTime() / 1000);
        LogResult L =service.QueryLog(StartTimestamp, EndTimestamp);
        System.out.println(L);
        return ResponseEntity.status(Http_OK).body(service.QueryLog(StartTimestamp, EndTimestamp));
    }

    @PostMapping("/alter/query")
    public ResponseEntity<AlterResult> QueryAlterData(@RequestBody Request request) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date StartDate = dateFormat.parse(request.getStart());
        int StartTimestamp = (int) (StartDate.getTime() / 1000);
        Date EndDate = dateFormat.parse(request.getEnd());
        int EndTimestamp = (int) (EndDate.getTime() / 1000);
        AlterResult A = service.QueryAlter(StartTimestamp, EndTimestamp);
        System.out.println(A);
        return ResponseEntity.status(Http_OK).body(service.QueryAlter(StartTimestamp, EndTimestamp));
    }


    @GetMapping("/alter/get")
    public List<Log> getWarningLogAll() {
        return service.GetWarningLogAll();
    }

    @GetMapping("/postlog/get")
    public List<Log> getPostLogAll() {
        return service.GetPostLogAll();
    }

    @GetMapping("/constraint/get")
    public ResponseEntity<List<MetricConstraint>> getConstraintAll() {
        List<MetricConstraint> constraintList = service.GetConstraintAll(0);
        return ResponseEntity.ok(constraintList);
    }

    @PutMapping("/constraint/update/{metric}")
    public ResponseEntity<Result> updateConstraint(@PathVariable String metric, @RequestBody MetricConstraint newData) {
        service.UpdateConstraint(metric, newData);
        return ResponseEntity.ok(new Result(UUID.randomUUID().toString(), "ok"));
    }

    @DeleteMapping("/constraint/delete/{metric}")
    public ResponseEntity<Result> deleteConstraint(@PathVariable String metric) {
        service.DeletConstraint(metric);
        return ResponseEntity.ok(new Result(UUID.randomUUID().toString(), "ok"));
    }

    @PutMapping("/task/put")
    public ResponseEntity<Result> changeScheduledTask(@RequestBody TaskSet taskSet) {
        service.ChangeTaskSet(taskSet.getInterval(), taskSet.getDate1());
        return ResponseEntity.ok(new Result(UUID.randomUUID().toString(), "ok"));
    }

    @PutMapping("/constraint/put")
    public ResponseEntity<Result> addConstraint(@RequestBody MetricConstraint metricConstraint) {
        service.AddConstraint(metricConstraint);
        return ResponseEntity.ok(new Result(UUID.randomUUID().toString(), "ok"));
    }
}
