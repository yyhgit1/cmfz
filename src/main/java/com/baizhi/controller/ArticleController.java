package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import com.baizhi.service.ArticleService;
import com.baizhi.service.GuruService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private GuruService guruService;

    @RequestMapping("edit")
    public Map del(String[] id, String oper) {
        Map map = new HashMap();
        if (oper.equals("del")) {
            for (String s : id) {
                articleService.delete(s);
            }
            map.put("status", "delok");
        }
        return map;
    }

    @RequestMapping("add")
    public Map add(Article article, MultipartFile inputfile, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        // System.out.println(inputfile);
        Map map = new HashMap();
        if (article.getId() == null || article.getId().equals("")) {
            String uuid = UUID.randomUUID().toString();
            article.setId(uuid);
            List<Guru> gurus = guruService.findAll();
            for (Guru guru : gurus) {
                if (guru.getId().equals(article.getGuru_id())) {
                    article.setGname(guru.getNick_name());
                }
            }
            // System.out.println(article);
            articleService.add(article);
            map.put("status", "addok");
            //获取路径
            String realPath = session.getServletContext().getRealPath("/back/upload/img");
            //判断路径文件夹是否存在
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            //防止重名操作
            //旧文件明
            String oldFileName = inputfile.getOriginalFilename();
            //新文件名前缀
            String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                    + UUID.randomUUID().toString().replace("-", "");
            //新文件名后缀
            String fileNameSuffix = "." + FilenameUtils.getExtension(oldFileName);
            //新文件名
            String newname = fileNamePrefix + fileNameSuffix;
            try {
                inputfile.transferTo(new File(realPath, newname));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //网络路径
            String http = request.getScheme();
            String local = InetAddress.getLocalHost().toString();
            Integer port = request.getServerPort();
            String path = request.getContextPath();
            String u = http + "://" + local.split("/")[1] + ":" + port + path + "/back/upload/img/" + newname;
            Article article1 = new Article();
            article1.setId(uuid);
            article1.setCover(u);
            // System.out.println(article1.getCover());
            articleService.update(article1);
        } else {
            if (inputfile.getOriginalFilename().equals("") || inputfile.getOriginalFilename() == null) {
                // System.out.println("没有修改图片");
                article.setCover(null);
                articleService.update(article);
            } else {
                //System.out.println("修改图片了");
                articleService.update(article);
                //获取路径
                String realPath = session.getServletContext().getRealPath("/back/upload/img");
                //判断路径文件夹是否存在
                File file = new File(realPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                //防止重名操作
                //旧文件明
                String oldFileName = inputfile.getOriginalFilename();
                //新文件名前缀
                String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                        + UUID.randomUUID().toString().replace("-", "");
                //新文件名后缀
                String fileNameSuffix = "." + FilenameUtils.getExtension(oldFileName);
                //新文件名
                String newname = fileNamePrefix + fileNameSuffix;
                try {
                    inputfile.transferTo(new File(realPath, newname));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //网络路径
                String http = request.getScheme();
                String local = InetAddress.getLocalHost().toString();
                Integer port = request.getServerPort();
                String path = request.getContextPath();
                String u = http + "://" + local.split("/")[1] + ":" + port + path + "/back/upload/img/" + newname;
                Article article1 = new Article();
                article1.setId(article.getId());
                article1.setCover(u);
                // System.out.println(article1.getCover());
                articleService.update(article1);
            }
            //articleService.findArticle(article.getId());
            //System.out.println(article);
        }
        return map;
    }

    @RequestMapping("findAll")
    public Map findAll(Integer page, Integer rows) {
        Map map = articleService.findAll(page, rows);
        return map;
    }

    @RequestMapping("insertArticle")
    public Map insertArticle(Article article) {
        Map map = new HashMap();
        return map;
    }

    @RequestMapping("upload")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        try {
            //获取路径
            String realPath = session.getServletContext().getRealPath("/back/upload/imgs");
            //判断路径文件夹是否存在
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            //防止重名操作
            //旧文件明
            String oldFileName = imgFile.getOriginalFilename();
            //新文件名前缀
            String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            //新文件名后缀
            String fileNameSuffix = "." + FilenameUtils.getExtension(oldFileName);
            //新文件名
            String newname = fileNamePrefix + fileNameSuffix;
            try {
                imgFile.transferTo(new File(realPath, newname));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //网络路径
            String http = request.getScheme();
            String local = InetAddress.getLocalHost().toString();
            Integer port = request.getServerPort();
            String path = request.getContextPath();
            String u = http + "://" + local.split("/")[1] + ":" + port + path + "/back/upload/imgs/" + newname;
            hashMap.put("error", 0);
            hashMap.put("url", u);
        } catch (Exception e) {
            hashMap.put("error", 1);
            hashMap.put("message", "上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }

    @RequestMapping("showImgs")
    public Map showAllImgs(HttpSession session, HttpServletRequest request) {
        // 1. 获取文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/back/upload/imgs/");
        // 2. 准备返回的Json数据
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        // 3. 获取目标文件夹
        File file = new File(realPath);
        File[] files = file.listFiles();
        // 4. 遍历文件夹中的文件
        for (File file1 : files) {
            // 5. 文件属性封装
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir", false);
            fileMap.put("has_file", false);
            fileMap.put("filesize", file1.length());
            fileMap.put("is_photo", true);
            // 获取文件后缀 | 文件类型
            String extension = FilenameUtils.getExtension(file1.getName());
            fileMap.put("filetype", extension);
            fileMap.put("filename", file1.getName());
            // 获取文件上传时间 1. 截取时间戳 2. 创建格式转化对象 3. 格式类型转换
            String s = file1.getName().split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date());
            fileMap.put("datetime", format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list", arrayList);
        hashMap.put("total_count", arrayList.size());
        // 返回路径为 项目名 + 文件夹路径
        hashMap.put("current_url", request.getContextPath() + "/back/upload/imgs/");
        return hashMap;
    }
}
