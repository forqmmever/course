package com.course.controller;

import com.course.entity.Log;
import com.course.dto.Result;
import com.course.entity.MetricConstraint;
import com.course.entity.TaskSet;
import com.course.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        Integer code = result.toString().equals("") ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        String msg = result.toString().equals("") ? "ok" : result.toString();
        Result responseBody = new Result(UUID.randomUUID().toString(), msg, code);
        return ResponseEntity.status(code).body(responseBody);
    }

    @GetMapping("/warning/get")
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
        return ResponseEntity.ok(new Result(UUID.randomUUID().toString(), "ok", HttpStatus.OK.value()));
    }

    @DeleteMapping("/constraint/delete/{metric}")
    public ResponseEntity<Result> deleteConstraint(@PathVariable String metric) {
        service.DeletConstraint(metric);
        return ResponseEntity.ok(new Result(UUID.randomUUID().toString(), "ok", HttpStatus.OK.value()));
    }

    @PutMapping("/task/put")
    public ResponseEntity<Result> changeScheduledTask(@RequestBody TaskSet taskSet) {
        service.ChangeTaskSet(taskSet.getInterval(), taskSet.getDate1());
        return ResponseEntity.ok(new Result(UUID.randomUUID().toString(), "ok", HttpStatus.OK.value()));
    }

    @PutMapping("/constraint/put")
    public ResponseEntity<Result> addConstraint(@RequestBody MetricConstraint metricConstraint) {
        service.AddConstraint(metricConstraint);
        return ResponseEntity.ok(new Result(UUID.randomUUID().toString(), "ok", HttpStatus.OK.value()));
    }
}
