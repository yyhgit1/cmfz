package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //添加专辑
    public Map add(Album album);

    //修改专辑
    public Map update(Album album);

    //删除专辑根据ID
    public Map delete(String id);

    //查询所有专辑
    public Map findAll(Integer page, Integer rows);

    //根据id查询专辑
    public Album findAlbum(String id);

    //查询所有专辑
    public List<Album> findAlbums();
}
