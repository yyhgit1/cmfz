package com.baizhi.controller;

import com.baizhi.entity.MapVo;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public Map login(User user, HttpServletRequest request) {
        Map map = new HashMap();
        HttpSession session = request.getSession();
        //  System.out.println("========");
        //  System.out.println(phone+"====="+password);
      /*  User user = new User();
        user.setPhone(phone);
        user.setPassword(password);*/
        try {
            User loginUser = userService.login(user);
            // System.out.println(loginUser);
            session.setAttribute("loginManager", loginUser);
            map.put("loginUser", loginUser);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            String message = e.getMessage();
            map.put("message", message);
            map.put("status", "-200");
            return map;
        }
    }

    @RequestMapping("cope")
    public Map cope(String phone) {
        Map map = new HashMap();

        return map;
    }


    @RequestMapping("findAll")
    public Map findAll(Integer page, Integer rows) {
        Map map = userService.findAll(page, rows);
        return map;
    }

    @RequestMapping("update")
    public Map update(User user) {
        Map map = new HashMap();
        User user1 = userService.findUser(user.getId());
        if ("正常".equals(user1.getStatus())) {
            userService.update(user1.setStatus("冻结"));
            map.put("status", "success");
        } else {
            userService.update(user1.setStatus("正常"));
            map.put("status", "success");
        }
        return map;
    }

    @RequestMapping("findUsers")
    public Map findUsers() {
        Map map = new HashMap();
        List<Integer> mans = new ArrayList<>();
        List<Integer> womens = new ArrayList<>();
        Integer man1 = userService.findUsers("男", 1);
        Integer man2 = userService.findUsers("男", 7);
        Integer man3 = userService.findUsers("男", 30);
        Integer man4 = userService.findUsers("男", 365);
        mans.add(man1);
        mans.add(man2);
        mans.add(man3);
        mans.add(man4);
        map.put("man", mans);
        Integer women1 = userService.findUsers("女", 1);
        Integer women2 = userService.findUsers("女", 7);
        Integer women3 = userService.findUsers("女", 30);
        Integer women4 = userService.findUsers("女", 365);
        womens.add(women1);
        womens.add(women2);
        womens.add(women3);
        womens.add(women4);
        map.put("women", womens);
        return map;
    }

    @RequestMapping("map")
    public Map findMap() {
        Map map = new HashMap();
        List<MapVo> mapvos = userService.findMap();
        map.put("map", mapvos);
        return map;
    }
}
