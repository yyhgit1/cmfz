package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    @ResponseBody
    public String login(Admin admin, String message, String number, HttpServletRequest request) {
        // System.out.println(admin);
        HttpSession session = request.getSession();
        //从Session中取出验证码进行对比
        String numb = (String) session.getAttribute("securityCode");
        //进行判断看是否在数据库中
        if (numb.equals(number)) {
            try {
                Admin login = adminService.login(admin);
                session.setAttribute("loginManager", login);
                message = "ok";
            } catch (Exception e) {
                message = e.getMessage();
            }
        } else {
            message = "验证码错误";
        }
        return message;
    }

    @RequestMapping("exit")
    public String exit(HttpServletRequest request) {
        //System.out.println("=======");
        HttpSession session = request.getSession();
        Admin loginManager = (Admin) session.getAttribute("loginManager");
        if (loginManager == null) {
            return "redirect:/back/user/login.jsp";
        } else {
            session.removeAttribute("loginManager");
            return "redirect:/back/user/login.jsp";
        }

    }

    @RequestMapping("img")
    public String captchaAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //生成验证码随机数
        String securityCode = SecurityCode.getSecurityCode();
        HttpSession session = request.getSession();
        session.setAttribute("securityCode", securityCode);
        //绘制生成验证码图片
        BufferedImage image = SecurityImage.createImage(securityCode);
        //响应到客户端
        OutputStream out = response.getOutputStream();
        //第一个参数，指定验证码图片对象，第二个，图片的格式，第三个、指定输出流
        ImageIO.write(image, "png", out);
        //不做跳转
        return null;
    }
}
