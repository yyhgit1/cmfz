package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //添加文章内容
    public Map add(Article article);

    //修改文章内容
    public Map update(Article article);

    //根据文章ID删除文章
    public Map delete(String id);

    //查询所有文章信息
    public Map findAll(Integer page, Integer rows);

    //根据id查询文章信息
    public Map findArticle(String id);

    //查询所有文章
    public List<Article> findArticles();

    //查询所有文章根据上师id
    public List<Article> findByArticles(String guru_id);
}
