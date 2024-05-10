package com.course.service;

import com.course.pojo.Log;
import com.course.pojo.PostLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduledTaskManager {
    @Autowired
    private  Service service;
    @Autowired
    private TaskScheduler taskScheduler;
    private final String[] MemoryNameList = {"node_memory_Buffers_bytes", "node_memory_Cached_bytes", "node_memory_MemFree_bytes", "node_memory_MemTotal_bytest"};
    private Date start;
    private ScheduledFuture<?> scheduledFuture;

    // 默认定时任务的执行间隔为5秒
    private int interval = 5000;

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
//        System.out.println(start);
        StartTask(); // 重新启动定时任务
    }

    // 定时任务执行的方法
    private void executeTask() {
        System.out.println("当前时间：" + new Date());
        Log MemorySum = service.GetMemorySum(MemoryNameList);
        MemorySum.setMetric("memory");
        service.CheckRules(service.GetMetricConstraint("memory"),MemorySum);
    }
}
