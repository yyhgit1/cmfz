package com.baizhi.service;

import com.baizhi.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    //添加上师
    public Map add(Guru guru);

    //修改上师
    public Map update(Guru guru);

    //删除上师根据ID
    public Map delete(String id);

    //查询所有上师分页
    public Map findAll(Integer page, Integer rows);

    //查询所有上师
    public List<Guru> findAll();
}
