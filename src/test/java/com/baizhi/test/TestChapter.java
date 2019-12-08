package com.baizhi.test;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestChapter {
    @Autowired
    private ChapterDao chapterDao;

    @Test
    public void TestC() {
        Chapter chapter = new Chapter();
        chapter.setAlbum_id("1");
        List<Chapter> cs = chapterDao.select(chapter);
        cs.forEach(c -> System.out.println(c));
    }
}
