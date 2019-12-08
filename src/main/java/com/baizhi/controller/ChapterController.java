package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("findAll")
    public Map findAll(Chapter chapter, Integer page, Integer rows) {
        Map map = chapterService.findAll(chapter, page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map edit(String id, Chapter chapter, String oper) {
        Map map = new HashMap();
        String uid = UUID.randomUUID().toString();
        if (oper.equals("add")) {
            chapter.setId(uid);
            //chapter.setCreate_time(new Date());
            // System.out.println("添加的轮播图对象"+banner);
            map = chapterService.add(chapter);
        } else if (oper.equals("del")) {
            chapterService.delete(id);
            map.put("status", "delok");
        } else if (oper.equals("edit")) {
            chapter.setUrl(null);
            chapterService.update(chapter);
            map.put("status", "editok");
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile url, String id, HttpSession session, HttpServletRequest request) throws UnknownHostException, UnknownHostException {
        //获取路径
        String realPath = session.getServletContext().getRealPath("/back/upload/mp3");
        //判断路径文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //防止重名操作
        //旧文件明
        String oldFileName = url.getOriginalFilename();
     /*   //新文件名前缀
        String fileNamePrefix=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + UUID.randomUUID().toString().replace("-","");
        //新文件名后缀
        String fileNameSuffix="."+ FilenameUtils.getExtension(oldFileName);
        //新文件名
        String newname=fileNamePrefix+fileNameSuffix;*/
        File fe = new File(realPath, oldFileName);
        try {
            url.transferTo(fe);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String time = "";
        try {
            MP3File f = (MP3File) AudioFileIO.read(fe);
            MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
            int t = audioHeader.getTrackLength();
            int b = 0;
            if (t % 60 == 0) {
                t = t / 60;
            } else {
                t = t / 60;
                if (t % 60 < 10) {
                    b = t % 60;
                    time = t + "分" + "0" + b + "秒";
                } else {
                    b = t % 60;
                    time = t + "分" + b + "秒";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //网络路径
        String http = request.getScheme();
        String local = InetAddress.getLocalHost().toString();
        Integer port = request.getServerPort();
        String path = request.getContextPath();
        String u = http + "://" + local.split("/")[1] + ":" + port + path + "/back/upload/mp3/" + oldFileName;
        double b = fe.length() / 1024;
        b = b / 1024;
        DecimalFormat df = new DecimalFormat(".00");
        String size = String.valueOf(df.format(b)) + "MB";
        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setUrl(u);
        chapter.setSize(size);
        chapter.setTime(time);
        chapterService.update(chapter);
    }

    @RequestMapping("down")
    public String down(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //根据相对目录获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/back/upload/mp3");
        String[] split = url.split("/");
        String fileName = split[split.length - 1];
        //System.out.println(fileName);
        //读取下载的指定文件
        File file = new File(realPath, fileName);
        //获取文件输入流
        FileInputStream is = new FileInputStream(file);
        //默认下载方式是在线打开 inline   附件形式下载  attachment
        response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        //响应输出流
        ServletOutputStream os = response.getOutputStream();
        //IO拷贝  使用工具类
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
        return null;
    }
}
