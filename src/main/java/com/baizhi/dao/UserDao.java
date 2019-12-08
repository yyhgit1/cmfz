package com.baizhi.dao;

import com.baizhi.entity.MapVo;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    //用户注册趋势
    public Integer findUsers(@Param("sex") String sex, @Param("day") Integer day);

    //用户分布图
    public List<MapVo> findMap();
}
