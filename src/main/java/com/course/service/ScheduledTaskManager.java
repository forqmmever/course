package com.course.service;

import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import com.course.utils.Aggregation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduledTaskManager {
    @Autowired
    private Service service;
    @Autowired
    private TaskScheduler taskScheduler;
    private final String[] MemoryNameList = {"node_memory_Buffers_bytes", "node_memory_Cached_bytes", "node_memory_MemFree_bytes"};
    private Date start;
    private ScheduledFuture<?> scheduledFuture;
    private static int LatestTimestamp = -1;

    private int interval = 2000;

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
        interval = Interval * 1000; // 修改参数
        StartTask(); // 重新启动定时任务
    }

    public void ChangeInitialDelay(Date Start) {
        StopTask(); // 先停止定时任务
        start = Start; // 修改参数
        StartTask(); // 重新启动定时任务
    }


    // 定时任务执行的方法
    private void executeTask() {
        System.out.println("当前时间：" + new Date());
        List<MetricConstraint> metricConstraintList = service.GetConstraintAll(1);
        if (metricConstraintList == null) return;
        int nowTimestamp = 0;
        for (MetricConstraint constraint : metricConstraintList) {
            nowTimestamp = CheckConstraint(constraint);
        }
        LatestTimestamp = nowTimestamp;
    }

    private int CheckConstraint(MetricConstraint metricConstraint) {
//        System.out.println(new Date());
        String metric = metricConstraint.getMetric();
        String expression = metricConstraint.getConstraintType();
        StringBuilder ConstraintType = new StringBuilder();
        String ConstraintDesciption = metricConstraint.getDescription();

        String tagJson = "";
        int timestamp = 0;

        float ConstraintValue = metricConstraint.getValue();

        char[] tokens = expression.toCharArray();

        // 用于存储操作数的栈
        Stack<Float> values = new Stack<>();

        // 用于存储操作符的栈
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;

            //数字
            if (Character.isDigit(tokens[i])) {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && Character.isDigit(tokens[i])) {
                    sb.append(tokens[i++]);
                }
                i--;
                values.push(Float.parseFloat(sb.toString()));
            }
            //字符
            else if (Character.isLetter(tokens[i])) {
                float num = 0;

                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && (Character.isLetter(tokens[i]) || tokens[i] == '_')) {
                    sb.append(tokens[i++]);
                }

                // 聚合操作预处理
                if (sb.toString().equals("max")
                        || sb.toString().equals("min")
                        || sb.toString().equals("sum")
                        || sb.toString().equals("avg")
                        || sb.toString().equals("rate")) {
                    i++;
                    StringBuilder strMetric = new StringBuilder();
                    while (i < tokens.length && (Character.isLetter(tokens[i]) || tokens[i] == '_')) {
                        strMetric.append(tokens[i++]);
                    }
                    //token[i..]='['
                    i++;

                    StringBuilder strRage = new StringBuilder();
                    while (i < tokens.length && Character.isDigit(tokens[i])) {
                        strRage.append(tokens[i++]);
                    }

                    int rage = Integer.parseInt(strRage.toString()) * (tokens[i] == 's' ?
                            1 : tokens[i] == 'm' ?
                            60 : tokens[i] == 'h' ?
                            60 * 60 : 24 * 60 * 60);

                    //第一次读取Log获取主机名 时间戳
                    if (timestamp == 0) {
                        Log log = service.GetLatestLog(strMetric.toString());
                        timestamp = log.getTimestamp();
                        //判断该时间戳数据是否检查过
                        if (timestamp == LatestTimestamp) return timestamp;
                        tagJson = log.getTagJson();
                    }

                    List<Float> ValueList = new ArrayList<>(service.GetLogValueByRage(strMetric.toString(), timestamp, rage));
                    switch (sb.toString()) {
                        case "max":
                            num = Aggregation.MaxValue(ValueList);
                            break;
                        case "min":
                            num = Aggregation.MinValue(ValueList);
                            break;
                        case "sum":
                            num = Aggregation.SumValue(ValueList);
                            break;
                        case "avg":
                            num = Aggregation.AvgValue(ValueList);
                            break;
                        case "rate":
                            num = Aggregation.RateValue(ValueList) / rage;
                            break;
                    }
                    //tokens[i..] == '])...'
                    i += 2;

                } else {
                    if (timestamp == 0) {
                        Log log = service.GetLatestLog(sb.toString());
                        timestamp = log.getTimestamp();
                        if (LatestTimestamp == timestamp) return timestamp;
                        tagJson = log.getTagJson();
                        num = log.getValue();
                    } else {
                        num = service.GetLogValue(sb.toString(), timestamp);
                    }
                    i--; // 因为for循环还会执行一次i++
                }
                values.push(num);
            } else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            }
            // 如果当前字符是操作符，将其与栈顶的操作符进行比较
            // 如果当前操作符的优先级小于或等于栈顶操作符的优先级，则弹出栈顶操作符和栈顶两个操作数，计算结果并入栈
            // 否则，将当前操作符入栈
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/') {
                while (!operators.isEmpty() && hasPrecedence(tokens[i], operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(tokens[i]);
            }
            //
            else {
                ConstraintType.append(tokens[i]);
            }
        }

        // 处理剩余的操作符
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        float ans = values.pop();
        service.CheckRules(ConstraintType.toString(), ConstraintValue, ConstraintDesciption, new Log(metric, tagJson, timestamp, ans));
        return timestamp;
    }

    // 返回运算符op1和op2的优先级是否小于等于
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        return true;
    }

    // 应用运算符op到操作数a和b
    private static float applyOperator(char op, float b, float a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new ArithmeticException("Division by zero");
                return a / b;
        }
        return 0;
    }
}
