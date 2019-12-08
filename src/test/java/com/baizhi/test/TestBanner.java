package com.baizhi.test;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBanner {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;

    @Test
    public void testFindAll() {
        Banner banner = new Banner();
        banner.setId("1");
        banner.setDate(new Date());
        bannerService.add(banner);
    }

}
