package com.course.service;

import com.course.entity.Log;
import com.course.entity.MetricConstraint;
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
        interval = Interval * 1000; // 修改参数
        StartTask(); // 重新启动定时任务
    }

    public void ChangeInitialDelay(Date Start) {
        StopTask(); // 先停止定时任务
        start = Start; // 修改参数
        StartTask(); // 重新启动定时任务
    }

    public void ChangeRate(int rate) {
        this.rate = rate;
    }

    // 定时任务执行的方法
    public void executeTask() {
        System.out.println("当前时间：" + new Date());
        List<MetricConstraint> metricConstraintList = service.GetConstraintAll(1);
        for (MetricConstraint constraint : metricConstraintList){
            CheckConstraint(constraint);
        }
    }

    private void CheckConstraint(MetricConstraint metricConstraint){
        String metric = metricConstraint.getMetric();
        String expression = metricConstraint.getConstraintType();
        String tagJson = "";
        StringBuilder ConstraintType = new StringBuilder();
        String ConstraintDesciption = metricConstraint.getDescription();
        int timestamp = 0;
        float ConstraintValue = metricConstraint.getValue();

        char[] tokens = expression.toCharArray();

        // 用于存储操作数的栈
        Stack<Float> values = new Stack<>();

        // 用于存储操作符的栈
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            // 如果当前字符是空格，则忽略
            if (tokens[i] == ' ')
                continue;

            // 如果当前字符是数字，则将连续的数字字符转换为数字，并入栈
            if (Character.isDigit(tokens[i])) {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && Character.isDigit(tokens[i])) {
                    sb.append(tokens[i++]);
                }
                i--; // 因为for循环还会执行一次i++
                values.push(Float.parseFloat(sb.toString()));
            }
            else if (Character.isLetter(tokens[i])){
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && (Character.isLetter(tokens[i]) || tokens[i] == '_')) {
                    sb.append(tokens[i++]);
                }
                i--; // 因为for循环还会执行一次i++
                System.out.println(metric + "_" + sb.toString());
                Log item =  service.GetPostLog(metric + "_" + sb.toString());
                System.out.println(item);
                if (tagJson.isEmpty()) {
                    tagJson = item.getTagJson();
                    timestamp = item.getTimestamp();
                }
                values.push(item.getValue());
            }

            // 如果当前字符是'('，则将其入栈
            else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            }

            // 如果当前字符是')'，则弹出栈顶的操作符和栈顶的两个操作数，计算结果并入栈
            else if (tokens[i] == ')') {
                while (operators.peek() != '(') {
                    values.push( applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // 弹出'('
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
            else {
                ConstraintType.append(tokens[i]);
            }
        }

        // 处理剩余的操作符
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        // 最终结果在栈顶
        float ans = values.pop();
        System.out.println(ans);
        service.CheckRules(ConstraintType.toString(),ConstraintValue,ConstraintDesciption,new Log(metric,tagJson,timestamp,ans));
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
