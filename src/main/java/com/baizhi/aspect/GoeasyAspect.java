package com.baizhi.aspect;

import com.baizhi.entity.MapVo;
import com.baizhi.service.UserService;
import com.google.gson.Gson;
import io.goeasy.GoEasy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect //切点
@Configuration
public class GoeasyAspect {
    @Autowired
    HttpServletRequest request;
    @Autowired
    private UserService userService;

    @Around("@annotation(com.baizhi.annotation.GoeasyAnnotation)")
    public Object findGoeasy(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object proceed = proceedingJoinPoint.proceed();
            String status = "success";
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
            Integer women1 = userService.findUsers("女", 1);
            Integer women2 = userService.findUsers("女", 7);
            Integer women3 = userService.findUsers("女", 30);
            Integer women4 = userService.findUsers("女", 365);
            womens.add(women1);
            womens.add(women2);
            womens.add(women3);
            womens.add(women4);
            map.put("women", womens);
            map.put("man", mans);
            //实时跟新用户注册趋势
            List<MapVo> mapvos = userService.findMap();
            map.put("map", mapvos);
            Gson gson = new Gson();
            String s = gson.toJson(map);
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-f35886506227474a82bec38703ee6d43");
            goEasy.publish("cmfz", s);
            //System.out.println("触发了goeasy======================");
            return null;
        } catch (Throwable throwable) {
            String status = "error";
            throwable.printStackTrace();
            return null;
        }
    }
}
