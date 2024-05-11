package com.course.web;

import com.course.mapper.Mapper;
import com.course.pojo.MetricConstraint;

import com.course.service.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class WebApplicationTests {
    @Autowired
    private Mapper mapper;
    @Autowired
    private Service service;

    @Test
    void contextLoads() {
        String url = "jdbc:mysql://localhost:3306/hostlog";
        String username = "root";
        String password = "123456";

        // 尝试连接数据库
        try {
            // 加载数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            Connection connection = DriverManager.getConnection(url, username, password);

            // 如果连接成功，输出连接成功的消息
            System.out.println("数据库连接成功！");

            // 可以在这里执行一些查询操作来验证连接是否正常
            // ...

            // 关闭数据库连接
            connection.close();
        } catch (ClassNotFoundException e) {
            // 如果找不到数据库驱动程序类，输出错误信息
            System.err.println("找不到数据库驱动程序类！");
            e.printStackTrace();
        } catch (SQLException e) {
            // 如果连接数据库失败，输出错误信息
            System.err.println("连接数据库失败！");
            e.printStackTrace();
        }
    }

    @Test
    public void tesSelect() {
//          System.out.println(mapper.GetMetricConstraint("up"));
          System.out.println(service.GetPostLog("up", "7273a1ea-0089-4674-b606-b1b8d809d866",1631762560));
//        System.out.println(mapper.GetWarningLogAll());
    }

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
    public void testGettime(){
        long time = mapper.GetStartTime() * 1000L;
        System.out.println(time);
        System.out.println(new Date(time));
    }
    @Test void testGetMemory(){
        String[] MemoryNameList = {"node_memory_Buffers_bytes", "node_memory_Cached_bytes", "node_memory_MemFree_bytes", "node_memory_MemTotal_bytest"};
        for (String memoryName : MemoryNameList){
            System.out.println(mapper.GetMemoryLog(memoryName));
        }
    }
    @Test
    public void testGetreceive(){
        System.out.println((mapper.GetNetworkReceive(60)));
        System.out.println((mapper.GetNetworkReceive(0)));
    }
}
