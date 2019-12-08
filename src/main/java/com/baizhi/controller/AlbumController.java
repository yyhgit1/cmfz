package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("findAll")
    public Map findAll(Integer page, Integer rows) {
        Map map = albumService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map edit(Album album, String oper) {
        Map map = new HashMap();
        String uid = UUID.randomUUID().toString();
        if (oper.equals("add")) {
            album.setId(uid);
            album.setDate(new Date());
            // System.out.println("添加的轮播图对象"+banner);
            map = albumService.add(album);
        } else if (oper.equals("del")) {
            albumService.delete(album.getId());
            chapterService.deleteAlbumId(album.getId());
            map.put("status", "delok");
        } else if (oper.equals("edit")) {
            album.setCover(null);
            albumService.update(album);
            map.put("status", "editok");
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile cover, String id, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        //获取路径
        String realPath = session.getServletContext().getRealPath("/back/upload/header");
        //判断路径文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //防止重名操作
        //旧文件明
        String oldFileName = cover.getOriginalFilename();
        //新文件名前缀
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + UUID.randomUUID().toString().replace("-", "");
        //新文件名后缀
        String fileNameSuffix = "." + FilenameUtils.getExtension(oldFileName);
        //新文件名
        String newname = fileNamePrefix + fileNameSuffix;
        try {
            cover.transferTo(new File(realPath, newname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //网络路径
        String http = request.getScheme();
        String local = InetAddress.getLocalHost().toString();
        Integer port = request.getServerPort();
        String path = request.getContextPath();
        String u = http + "://" + local.split("/")[1] + ":" + port + path + "/back/upload/header/" + newname;
        Album album = new Album();
        album.setId(id);
        album.setCover(u);
        albumService.update(album);
    }
}
