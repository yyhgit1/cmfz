package com.baizhi.test;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestArticle {
    @Autowired
    private ArticleService articleService;

    @Test
    public void test() {
        Map map = articleService.findArticle("24342faf-0c0d-4b66-a182-63966c76ff15");
        System.out.println(map.containsKey("article"));
        Article article = (Article) map.get("article");
        System.out.println(article.getContent());
    }

    @Test
    public void testa() {
        List<Article> articles = articleService.findByArticles("1");
        for (Article article : articles) {
            System.out.println(article);
        }
    }
}
