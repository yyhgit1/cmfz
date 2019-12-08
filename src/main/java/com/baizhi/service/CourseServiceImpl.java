package com.baizhi.service;

import com.baizhi.dao.CourseDao;
import com.baizhi.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    //添加功课
    public Map addCourse(Course course) {
        Map map = new HashMap();
        courseDao.insert(course);
        map.put("status", "addok");
        return map;
    }

    //根据id删除功课
    public Map delete(String id) {
        Map map = new HashMap();
        courseDao.deleteByPrimaryKey(id);
        map.put("status", "delok");
        return map;
    }

    //查询该用户下的所有功课
    public List<Course> findAll() {
        return courseDao.selectAll();
    }

    //查询该用户下的所有功课
    public List<Course> findCourses(String uid) {
        Example example = new Example(Course.class);
        example.createCriteria().andEqualTo("user_id", uid).orEqualTo("type", "必修");
        return courseDao.selectByExample(example);
    }
}
