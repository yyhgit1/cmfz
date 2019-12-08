package com.baizhi.controller;

import com.baizhi.entity.Guru;
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
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;

    @RequestMapping("findAll")
    public List<Guru> findAll() {
        return guruService.findAll();
    }

    @RequestMapping("findAllf")
    public Map findAll(Integer page, Integer rows) {
        Map map = guruService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map edit(Guru guru, String oper) {
        Map map = new HashMap();
        if (oper.equals("add")) {
            map = guruService.add(guru);
        } else if (oper.equals("del")) {
            guruService.delete(guru.getId());
            map.put("status", "delok");
        } else if (oper.equals("edit")) {
            guru.setPhoto(null);
            guruService.update(guru);
            map.put("status", "editok");
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile photo, String id, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        //获取路径
        String realPath = session.getServletContext().getRealPath("/back/upload/guruimg");
        //判断路径文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //防止重名操作
        //旧文件明
        String oldFileName = photo.getOriginalFilename();
        //新文件名前缀
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + UUID.randomUUID().toString().replace("-", "");
        //新文件名后缀
        String fileNameSuffix = "." + FilenameUtils.getExtension(oldFileName);
        //新文件名
        String newname = fileNamePrefix + fileNameSuffix;
        try {
            photo.transferTo(new File(realPath, newname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //网络路径
        String http = request.getScheme();
        String local = InetAddress.getLocalHost().toString();
        Integer port = request.getServerPort();
        String path = request.getContextPath();
        String u = http + "://" + local.split("/")[1] + ":" + port + path + "/back/upload/guruimg/" + newname;
        Guru guru = new Guru();
        guru.setId(id);
        guru.setPhoto(u);
        guruService.update(guru);
    }
}
