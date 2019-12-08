package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    //添加轮播图
    public Map add(Banner banner);

    //修改轮播图
    public Map update(Banner banner);

    //删除轮播图根据ID
    public Map delete(String id);

    //查询所有轮播图分页
    public Map findAll(Integer page, Integer rows);

    //查询所有轮播图不分页
    public List<Banner> findBanners();
}
