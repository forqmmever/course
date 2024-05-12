package com.course.service;

import com.course.entity.Log;

import com.course.entity.MetricConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduledTaskManager {
    @Autowired
    private  Service service;
    @Autowired
    private TaskScheduler taskScheduler;
    private final String[] MemoryNameList = {"node_memory_Buffers_bytes", "node_memory_Cached_bytes", "node_memory_MemFree_bytes"};
    private Date start;
    private ScheduledFuture<?> scheduledFuture;
    private int rate = 60;

    // 默认定时任务的执行间隔为5秒
    private int interval = 30000;

    public ScheduledTaskManager(Service service, TaskScheduler taskScheduler) {
        this.service = service;
        this.taskScheduler = taskScheduler;
    }

    // 启动定时任务
    public void StartTask() {
        scheduledFuture = taskScheduler.scheduleAtFixedRate(
                this::executeTask, start, interval);
    }

    // 停止定时任务
    public void StopTask() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
    }

    // 动态修改定时任务的参数
    public void ChangeInterval(int Interval) {
        StopTask(); // 先停止定时任务
        interval = Interval; // 修改参数
        StartTask(); // 重新启动定时任务
    }
    public void ChangeInitialDelay(Date Start){
        StopTask(); // 先停止定时任务
        start = Start; // 修改参数
        StartTask(); // 重新启动定时任务
    }
    public void ChangeRate(int rate){
        this.rate = rate;
    }
    // 定时任务执行的方法
    private void executeTask() {
        System.out.println("当前时间：" + new Date());
        CheckMemoryConstraint();
        CheckNetwokReceive();
    }

    private void CheckMemoryConstraint(){
        String tagJson = "";
        float memorySum = 0;
        int timestamp = 0;
        for (String memoryName : MemoryNameList){
            Log memoryLog = service.GetMemoryLog(memoryName);
            if (memoryLog == null){
                return;
            }
            tagJson = memoryLog.getTagJson();
            memorySum += memoryLog.getValue();
            timestamp = memoryLog.getTimestamp();
        }

        Log totalMemory = service.GetMemoryLog("node_memory_MemTotal_bytes");
        if (totalMemory== null){
            return;
        }
        float div = totalMemory.getValue();

        Log memoryCalculated = new Log();
        memoryCalculated.setMetric("memory");
        memoryCalculated.setTagJson(tagJson);
        memoryCalculated.setTimestamp(timestamp);
        memoryCalculated.setValue( (1- (memorySum / div)) * 100 );
        System.out.println(memoryCalculated);

//        MetricConstraint constraint = service.GetMetricConstraint("memory");
//        if (service.CheckRules(constraint,memoryCalculated)){
//            memoryCalculated.setDescription(constraint.getDescription());
//            memoryCalculated.setTime(new Date());
//            System.out.println(memoryCalculated);
//            service.SaveWarningLog(memoryCalculated);
//        }
        service.CheckRules(service.GetMetricConstraint("memory"),memoryCalculated);
    }

    private void CheckNetwokReceive(){
        Log startReceive = service.GetNetworkReceive(rate);
        if (startReceive == null){
            return;
        }
        Log endReceive = service.GetNetworkReceive(0);

        Log rateReceive = new Log();
        rateReceive.setMetric("rate_network_receive_bytes_total");
        rateReceive.setValue(endReceive.getValue() - startReceive.getValue());
        rateReceive.setTimestamp(endReceive.getTimestamp());
        rateReceive.setTagJson(endReceive.getTagJson());

//        MetricConstraint constraint= service.GetMetricConstraint("rate_network_receive_bytes_total");

        service.CheckRules(service.GetMetricConstraint("rate_network_receive_bytes_total"),rateReceive);
    }
}
