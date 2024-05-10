package com.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.ParseException;

@SpringBootApplication
@EnableScheduling
public class WebApplication {

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(WebApplication.class, args);
    }

}
