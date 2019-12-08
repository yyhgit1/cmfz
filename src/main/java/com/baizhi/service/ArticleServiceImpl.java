package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    //添加文章内容
    public Map add(Article article) {
        Map map = new HashMap();
        articleDao.insert(article);
        map.put("status", "addok");
        return map;
    }

    //修改文章内容
    public Map update(Article article) {
        Map map = new HashMap();
        articleDao.updateByPrimaryKeySelective(article);
        map.put("status", "updateok");
        return map;
    }

    //根据文章ID删除文章
    public Map delete(String id) {
        Map map = new HashMap();
        articleDao.deleteByPrimaryKey(id);
        map.put("status", "delok");
        return map;
    }

    //查询所有文章信息
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Article> articles = articleDao.selectByRowBounds(new Article(), new RowBounds((page - 1) * rows, rows));
        int records = articleDao.selectCount(new Article());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", articles);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    //根据id查询文章信息
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findArticle(String id) {
        Map map = new HashMap();
        Article article = articleDao.selectByPrimaryKey(id);
        map.put("article", article);
        return map;
    }

    //查询所有文章
    public List<Article> findArticles() {
        return articleDao.selectAll();
    }

    //查询所有文章根据上师id
    public List<Article> findByArticles(String guru_id) {
        Article article = new Article();
        article.setGuru_id(guru_id);
        return articleDao.select(article);
    }
}
