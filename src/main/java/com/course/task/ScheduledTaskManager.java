package com.course.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduledTaskManager {


    private TaskScheduler taskScheduler;

    @Autowired
    public void ScheduledTaskManager (TaskScheduler taskScheduler){
        this.taskScheduler  = taskScheduler;
    }
    private Date start;
    private ScheduledFuture<?> scheduledFuture;

    // 默认定时任务的执行间隔为5秒
    private int interval = 5000;

    // 启动定时任务
    public void StartTask() {
        scheduledFuture = taskScheduler.scheduleAtFixedRate(
                this::executeTask, new Date(), interval);
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

    // 定时任务执行的方法
    private void executeTask() {
//        System.out.println("当前时间：" + new Date());
    }
}
