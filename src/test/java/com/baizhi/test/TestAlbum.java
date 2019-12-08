package com.baizhi.test;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAlbum {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ChapterDao chapterDao;

    @Test
    public void testa() {
        Album album = albumDao.selectByPrimaryKey("1");
        Chapter chapter = new Chapter();
        chapter.setAlbum_id("1");
        List<Chapter> cs = chapterDao.select(chapter);
        album.setChapters(cs);
        System.out.println(album);
    }

    @Test
    public void testb() {
        //        创建 Example对象  负责条件查询
        Example example = new Example(Chapter.class);
//        按id查询
        example.createCriteria().andEqualTo("album_id", "1");
//        创建  RowBounds对象  负责分页查询   第一个参数index，第二个参数每页展示条数
        RowBounds rowBounds = new RowBounds(0, 2);
//        集成 按条件查询并分页  将两个对象放到继承Mapper接口的接口中
        List chapters = chapterDao.selectByExampleAndRowBounds(example, rowBounds);
        System.out.println(chapters);
    }
}
