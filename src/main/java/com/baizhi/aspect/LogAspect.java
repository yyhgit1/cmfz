package com.baizhi.aspect;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class LogAspect {
    @Autowired
    private LogService logService;
    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(com.baizhi.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) {
        /*
        谁   时间  事件   成功与否
        * */
        HttpSession session = request.getSession();
        //谁
        Admin loginManager = (Admin) session.getAttribute("loginManager");
        //时间
        Date date = new Date();
        //获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        //获取注解信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        //System.out.println(value);
        try {
            Object proceed = proceedingJoinPoint.proceed();
            String status = "success";
            // System.out.println(loginManager.getUsername()+"====="+date+name+"====="+status);
            Log log = new Log();
            log.setId(UUID.randomUUID().toString());
            log.setContent(value);
            log.setName("当前管理员为：" + loginManager.getUsername());
            log.setDate(date);
            log.setStatus("操作：" + status);
            logService.add(log);
            return proceed;
        } catch (Throwable throwable) {
            String status = "error";
            //System.out.println(loginManager.getUsername()+"====="+date+name+"====="+status);
            throwable.printStackTrace();
            Log log = new Log();
            log.setId(UUID.randomUUID().toString());
            log.setContent(value);
            log.setName("当前管理员为：" + loginManager.getUsername());
            log.setDate(date);
            log.setStatus("操作：" + status);
            logService.add(log);
            return null;
        }
    }
}
