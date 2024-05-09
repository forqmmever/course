package com.course.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class IntervalTask {

    private long initialDelay = -1923665080;

    // 任务执行间隔（毫秒）
    private long fixedDelay = 30000; // 30秒

    public void MyScheduledTasks(String SourceDate) throws ParseException {
        // 将开始时间字符串转换为Date对象
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = DateFormat.parse("2024-04-17 13:55:00");
        // 计算初始延迟（毫秒）
        initialDelay = startDate.getTime() - System.currentTimeMillis();
    }

//    @Scheduled(initialDelayString = "${scheduled.initialDelay}", fixedDelayString = "${scheduled.fixedDelay}")
    @Scheduled(initialDelayString = "-1923665080", fixedDelayString = "30000")
    public void task() {
        System.out.println("Task executed at " + new Date());
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public long getFixedDelay() {
        return fixedDelay;
    }

    public void setFixedDelay(long fixedDelay) {
        this.fixedDelay = fixedDelay;
    }
}
