package com.course.web;

import com.course.mapper.Mapper;
import com.course.pojo.MetricConstraint;
import com.course.pojo.PostLog;
import com.course.service.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    public void tesSelect(){
//        System.out.println(mapper.GetMetricConstraint("up"));
        System.out.println(service.GetPostLog("node_load5","7273a1ea-0089-4674-b606-b1b8d809d866"));
//        System.out.println(mapper.GetPostLog("node_load5","7273a1ea-0089-4674-b606-b1b8d809d866"));
    }
}
