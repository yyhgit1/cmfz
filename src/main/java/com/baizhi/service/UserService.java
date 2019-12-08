package com.baizhi.service;

import com.baizhi.entity.MapVo;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //登录
    public User login(User user);

    //添加用户
    public Map add(User user);

    //修改用户
    public Map update(User user);

    //删除用户根据ID
    public Map delete(String id);

    //查询所有用户分页
    public Map findAll(Integer page, Integer rows);

    //查询一个对象
    public User findUser(String id);

    //用户注册趋势
    public Integer findUsers(String sex, Integer day);

    //用户分布图
    public List<MapVo> findMap();
}
