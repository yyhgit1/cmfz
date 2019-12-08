package com.baizhi.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Banner1;
import com.baizhi.service.BannerService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("findAll")
    public Map findAll(Integer page, Integer rows) {
        Map map = bannerService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map edit(String[] id, Banner banner, String oper) {
        Map map = new HashMap();
        String uid = UUID.randomUUID().toString();
        if (oper.equals("add")) {
            banner.setId(uid);
            banner.setDate(new Date());
            // System.out.println("添加的轮播图对象"+banner);
            map = bannerService.add(banner);
        } else if (oper.equals("del")) {
            for (String s : id) {
                bannerService.delete(s);
            }
            map.put("status", "delok");
        } else if (oper.equals("edit")) {
            banner.setUrl(null);
            bannerService.update(banner);
            map.put("status", "editok");
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile url, String id, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        //获取路径
        String realPath = session.getServletContext().getRealPath("/back/upload/img");
        //判断路径文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //防止重名操作
        //旧文件明
        String oldFileName = url.getOriginalFilename();
        //新文件名前缀
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + UUID.randomUUID().toString().replace("-", "");
        //新文件名后缀
        String fileNameSuffix = "." + FilenameUtils.getExtension(oldFileName);
        //新文件名
        String newname = fileNamePrefix + fileNameSuffix;
        try {
            url.transferTo(new File(realPath, newname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //网络路径
        String http = request.getScheme();
        String local = InetAddress.getLocalHost().toString();
        Integer port = request.getServerPort();
        String path = request.getContextPath();
        String u = http + "://" + local.split("/")[1] + ":" + port + path + "/back/upload/img/" + newname;
        Banner banner = new Banner();
        banner.setId(id);
        banner.setUrl(u);
        bannerService.update(banner);
    }

    @RequestMapping("export")
    // @Scheduled(cron = "0 0 24 ? * 2")
    public Map export(HttpServletResponse response) throws IOException {
        ServletOutputStream os = response.getOutputStream();//客户端输出流
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("banner.xls", "utf-8"));
        Map map = new HashMap();
        List<Banner> banners = bannerService.findBanners();
        ArrayList<Banner1> banner1s = new ArrayList<>();
        for (Banner banner : banners) {
            Banner1 banner1 = new Banner1();
            banner1.setUr(new URL(banner.getUrl()));
            banner1.setId(banner.getId()).setTitle(banner.getTitle()).setUrl(banner.getUrl()).setDate(banner.getDate()).setDescription(banner.getDescription()).setHref(banner.getHref()).setStatus(banner.getStatus());
            banner1s.add(banner1);
        }
//        String  fileName="F:\\xls\\"+new Date().getTime()+"Banner.xlsx";
        //写法2
        ExcelWriter build = EasyExcel.write(os, Banner1.class).build();
        //String : 页名称  Int: 第几页    可以同时指定
        WriteSheet sheet = EasyExcel.writerSheet("轮播图信息").build();
        build.write(banner1s, sheet);
        build.finish();
        os.close();
        map.put("status", "ok");
        return map;
        //poi 写入 与 EasyExcel依赖有冲突
        /*//创建表格对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建页对象
        HSSFSheet sheet = workbook.createSheet("轮播图信息");
        //创建行对象
        HSSFRow row = sheet.createRow(0);
        //HSSFCellStyle cellStyle = workbook.createCellStyle();
        //cellStyle.setAlignment(HorizontalAlignment.CENTER);//文字剧中
        String[] strs={"ID","标题","图片路径","跳转路径","创建时间","描述","状态"};
        for(int i=0;i<strs.length;i++){
            //String str=strs[i];
            //给第一行赋值
            row.createCell(i).setCellValue(strs[i]);
        }
        //将集合中的数据写入到xls文件中
        HSSFDataFormat dataFormat=workbook.createDataFormat();
        //设置时间样式
        short format=dataFormat.getFormat("yyyy-MM-dd");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);
        List<Banner> banners = bannerService.findBanners();
        for (int i = 0; i < banners.size(); i++) {
            //依次创建行
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(banners.get(i).getId());
            row1.createCell(1).setCellValue(banners.get(i).getTitle());
            row1.createCell(2).setCellValue(banners.get(i).getUrl());
            row1.createCell(3).setCellValue(banners.get(i).getHref());
            HSSFCell cell=row1.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(banners.get(i).getDate());
            row1.createCell(5).setCellValue(banners.get(i).getDescription());
            row1.createCell(6).setCellValue(banners.get(i).getStatus());
        }
        String fileName="banner.xls";
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.close();
        workbook.close();*/
        //poi   写入绝对路径
       /* try {
            //将表格对象写入磁盘
            workbook.write(new File("F:\\xls\\banner.xls"));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }*/

    }
}
