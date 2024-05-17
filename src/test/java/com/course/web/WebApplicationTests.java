package com.course.web;

import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import com.course.mapper.Mapper;

import com.course.service.ScheduledTaskManager;
import com.course.service.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class WebApplicationTests {
    @Autowired
    private Mapper mapper;
    @Autowired
    private Service service;

    @Autowired
    private ScheduledTaskManager manager;


    @Test
    public void testTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdf.parse("2024-04-17 13:55:00");
        // 计算初始延迟（毫秒）
        long initialDelay = startDate.getTime() - System.currentTimeMillis();
        System.out.println(startDate.getTime());
        System.out.println(initialDelay);
    }

    @Test
    void testGetMemory() {
        System.out.println(mapper.GetConstraintAll(1));
        System.out.println(mapper.GetConstraintAll(0));
    }

    @Test
    public void testGetreceive() {
        System.out.println((mapper.GetNetworkReceive(60)));
        System.out.println((mapper.GetNetworkReceive(0)));
    }

    @Test
    public void testGetALL() {
        List<Log> ans = mapper.GetWarningLogAll();
        for (Log item : ans) {
//            System.out.println(item.getTags());
            System.out.println(item);
        }
    }

    @Test
    public void testUpdate() {
        MetricConstraint metricConstraint = new MetricConstraint("up", "=", 0, "宕机", 0);
        mapper.UpdateConstraint("up", metricConstraint);
    }

    @Test
    public void testQuery() {
        int StartTimestamp = 1631762560;
        int EndTimestamp = 1631762720;
        System.out.println(service.QueryLog(StartTimestamp,EndTimestamp));
        System.out.println("-----------------------------");
        System.out.println(service.QueryAlter(StartTimestamp, EndTimestamp));
    }
}
