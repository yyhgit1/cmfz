package com.baizhi.config;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TImerConfig {
    //@Scheduled(cron = "0 0 24 ? * 2")
    public void t1() {
        System.out.println(new Date());
    }
}
