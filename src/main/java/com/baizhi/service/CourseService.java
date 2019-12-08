package com.baizhi.service;

import com.baizhi.entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    //添加功课
    public Map addCourse(Course course);

    //根据id删除功课
    public Map delete(String id);

    //查询该用户下的所有功课
    public List<Course> findAll();

    //查询该用户下的所有功课
    public List<Course> findCourses(String uid);
}
