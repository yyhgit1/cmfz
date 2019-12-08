package com.baizhi.controller;

import com.baizhi.entity.Course;
import com.baizhi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    //添加功课
    @RequestMapping("add")
    public Map addCourse(String title, String uid) {
        Map map = new HashMap();
        Course course = new Course();
        course.setId(UUID.randomUUID().toString());
        course.setCreate_date(new Date());
        course.setUser_id(uid);
        course.setTitle(title);
        course.setType("选修");
        courseService.addCourse(course);
        List<Course> courses1 = courseService.findAll();
        List<Course> courses = new ArrayList<>();
        for (Course course1 : courses1) {
            if (course1.getUser_id().equals(uid) || course1.getType().equals("必修")) {
                courses.add(course1);
            }
        }
        map.put("courses", courses);
        map.put("status", "200");
        return map;
    }

    //接口8展示该用户下的功课
    @RequestMapping("show")
    public Map show(String uid) {
        //System.out.println(uid+"======");
        Map map = new HashMap();
        //普通方法
       /* List<Course> cs = courseService.findAll();
        List<Course> courses=new ArrayList<>();
        for (Course course1 : cs) {
            //System.out.println(course1+"=================");
            if(course1.getUser_id().equals(uid)||course1.getType().equals("必修")){
                courses.add(course1);
            }
        }*/
        //example方法
        List<Course> courses = courseService.findCourses(uid);
        map.put("courses", courses);
        map.put("status", "200");
        return map;
    }

    @RequestMapping("delete")
    public Map delete(String id, String uid) {
        Map map = new HashMap();
        courseService.delete(id);
       /* List<Course> cs = courseService.findAll();
        List<Course> courses=new ArrayList<>();
        for (Course course1 : cs) {
            if(course1.getUser_id().equals(uid)||course1.getType().equals("必修")){
                courses.add(course1);
            }
        }*/
        List<Course> courses = courseService.findCourses(uid);
        map.put("courses", courses);
        map.put("status", "200");
        return map;
    }
}
