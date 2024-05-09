package com.course.service;

import com.course.mapper.Mapper;
import com.course.pojo.PostLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

    private Mapper mapper;

    @Autowired
    public ServiceImpl(Mapper mapper){
        this.mapper = mapper;
    }

    public boolean SavePostLogPostLog(PostLog postLog) {
        mapper.SavePostLog(postLog);
        return true;
    }

    @Override
    public boolean GetPostLog(String metric, String instanceId) {
        String keyword = "%" + instanceId + "%";
        System.out.println(mapper.GetPostLog(metric,keyword));
        return true;
    }

    @Override
    public boolean GetMetricConstraint(String metric) {
        return false;
    }



}
