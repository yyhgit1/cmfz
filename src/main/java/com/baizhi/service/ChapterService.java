package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    //添加章节
    public Map add(Chapter chapter);

    //修改章节
    public Map update(Chapter chapter);

    //删除章节根据专辑ID
    public Map deleteAlbumId(String album_id);

    //根据章节Id删除章节
    public Map delete(String id);

    //查询所有专辑
    public Map findAll(Chapter chapter, Integer page, Integer rows);
}
