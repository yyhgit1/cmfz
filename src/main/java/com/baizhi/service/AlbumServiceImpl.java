package com.baizhi.service;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ChapterDao chapterDao;

    //添加专辑
    @LogAnnotation(value = "添加专辑信息")
    public Map add(Album album) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString();
        album.setId(s);
        albumDao.insert(album);
        map.put("id", s);
        map.put("status", "addok");
        return map;
    }

    //修改专辑
    @LogAnnotation(value = "修改专辑信息")
    public Map update(Album album) {
        Map map = new HashMap();
        albumDao.updateByPrimaryKeySelective(album);
        map.put("status", "updateok");
        return map;
    }

    //删除专辑根据ID
    @LogAnnotation(value = "删除专辑信息")
    public Map delete(String id) {
        Map map = new HashMap();
        albumDao.deleteByPrimaryKey(id);
        map.put("status", "delok");
        return map;
    }

    //查询所有专辑
    @Transactional(propagation = Propagation.SUPPORTS)
    //@LogAnnotation(value = "展示专辑信息")
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Album> albums = albumDao.selectByRowBounds(new Album(), new RowBounds((page - 1) * rows, rows));
        for (Album album : albums) {
            Chapter chapter = new Chapter();
            chapter.setAlbum_id(album.getId());
            List<Chapter> chapters = chapterDao.select(chapter);
            album.setCount(chapters.size());
            album.setChapters(chapters);
            Album al = new Album();
            al.setId(album.getId());
            al.setCount(chapters.size());
            albumDao.updateByPrimaryKeySelective(al);
        }
        int records = albumDao.selectCount(new Album());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", albums);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    //根据id查询专辑
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album findAlbum(String id) {
        Map map = new HashMap();
        Album album = albumDao.selectByPrimaryKey(id);
        Chapter chapter = new Chapter();
        chapter.setAlbum_id(album.getId());
        List<Chapter> chapters = chapterDao.select(chapter);
        album.setCount(chapters.size());
        album.setChapters(chapters);
        Album al = new Album();
        al.setId(album.getId());
        al.setCount(chapters.size());
        albumDao.updateByPrimaryKeySelective(al);
        return album;
    }

    //查询所有专辑
    public List<Album> findAlbums() {
        return albumDao.selectAll();
    }
}
