package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    //添加章节
    public Map add(Chapter chapter) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString();
        chapter.setId(s);
        chapterDao.insert(chapter);
        map.put("id", s);
        map.put("status", "addok");
        return map;
    }

    //修改章节
    public Map update(Chapter chapter) {
        Map map = new HashMap();
        chapterDao.updateByPrimaryKeySelective(chapter);
        map.put("status", "updateok");
        return map;
    }

    //删除章节根据专辑ID
    public Map deleteAlbumId(String album_id) {
        Map map = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setAlbum_id(album_id);
        chapterDao.delete(chapter);
        map.put("status", "delok");
        return map;
    }

    //根据章节Id删除章节
    public Map delete(String id) {
        Map map = new HashMap();
        chapterDao.deleteByPrimaryKey(id);
        map.put("status", "delok");
        return map;
    }

    public Map findAll(Chapter chapter, Integer page, Integer rows) {
        Map map = new HashMap();
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        int records = chapterDao.selectCount(chapter);
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", chapters);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }
}
