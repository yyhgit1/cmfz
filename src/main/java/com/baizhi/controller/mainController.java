package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Guru;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("main")
public class mainController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private GuruService guruService;

    //接口5一级页面展示接口
    @RequestMapping("show")
    public Map show(String object, String uid, String sub_type) {
        Map map = new HashMap();
        if (object.equals("all")) {
            //轮播图
            List<Banner> banners = new ArrayList<>();
            List<Banner> banners1 = bannerService.findBanners();
            for (Banner banner : banners1) {
                if (banner.getStatus().equals("1")) {
                    banners.add(banner);
                }
            }
            List<Banner> bannerList = new ArrayList<>();
            if (banners.size() < 5) {
                System.out.println(banners);
                map.put("banners", banners);
            } else {
                bannerList = banners.subList(0, 5);
                map.put("banners", bannerList);
            }

            List<Album> albums = new ArrayList<>();
            List<Album> albums1 = albumService.findAlbums();
            for (Album album : albums1) {
                if (album.getStatus().equals("展示")) {
                    albums.add(album);
                }
            }
            List<Album> albumList = new ArrayList<>();
            if (albums.size() < 6) {
                map.put("albums", albums);
            } else {
                albumList = albums.subList(0, 6);
                map.put("albums", albumList);
            }

            List<Article> articles1 = articleService.findArticles();
            List<Article> articles = new ArrayList<>();
            for (Article article : articles1) {
                if (article.getStatus().equals("展示")) {
                    articles.add(article);
                }
            }
            List<Article> articleList = new ArrayList<>();
            if (articles.size() < 6) {
                map.put("articles", articles);
            } else {
                articleList = articles.subList(0, 6);
                map.put("articles", articleList);
            }
            map.put("status", "200");
            return map;
        } else if (object.equals("wen")) {
            List<Album> albums = new ArrayList<>();
            List<Album> albums1 = albumService.findAlbums();
            for (Album album : albums1) {
                if (album.getStatus().equals("展示")) {
                    albums.add(album);
                }
            }
            map.put("albums", albums);
            map.put("status", "200");
            return map;
        } else if (object.equals("si")) {
            if (sub_type.equals("ssyj")) {
                Set<String> guruIds = stringRedisTemplate.opsForSet().members(uid);

                List<Article> articles = new ArrayList<>();
                for (String guruId : guruIds) {
                    List<Article> articles1 = articleService.findByArticles(guruId);
                    //把整个集合添加到另一个集合
                    articles.addAll(articles1);
                }
                map.put("articles", articles);
                map.put("status", "200");
                return map;
            }
            if (sub_type.equals("xmfy")) {
                List<Article> articles1 = articleService.findArticles();
                List<Article> articles = new ArrayList<>();
                for (Article article : articles1) {
                    if (article.getStatus().equals("展示")) {
                        articles.add(article);
                    }
                }
                map.put("articles", articles);
                map.put("status", "200");
                return map;
            }
        } else {
            return null;
        }
        return null;
    }

    //接口6展示文章详情
    @RequestMapping("article")
    public Map articleShow(String id, String uid) {
        Map map = new HashMap();
        Map articleMap = articleService.findArticle(id);
        Article article = (Article) articleMap.get("article");
        map.put("status", "200");
        map.put("article", article);
        return map;
    }

    //接口7展示专辑详情
    @RequestMapping("showAlbum")
    public Map showAlbum(String id, String uid) {
        Map map = new HashMap();
        Album album = albumService.findAlbum(id);
        map.put("album", album);
        map.put("status", "200");
        return map;
    }

    //接口17展示上师列表
    @RequestMapping("showGuru")
    public Map showGuru(String uid) {
        Map map = new HashMap();
        try {
            List<Guru> gurus = guruService.findAll();
            map.put("list", gurus);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("status", "-200");
            return map;
        }
    }

    //接口18添加关注上师
    @RequestMapping("guru")
    public Map addGuru(String uid, String id) {
        Map map = new HashMap();
        try {
            //对String类型的序列化
            stringRedisTemplate.setStringSerializer(new StringRedisSerializer());
            //对key序列化
            stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
            //添加redis的用户上师id
            stringRedisTemplate.opsForSet().add(uid, id);
            List<Guru> gurus = guruService.findAll();
            map.put("list", gurus);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("status", "-200");
            return map;
        }
    }
}
