package com.baizhi.test;

import com.baizhi.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAdminService {
    @Autowired
    private AdminService adminService;

    @Test
    public void test() {
      /*  Admin admin=null;
        System.out.println(adminService.login(admin));*/
    }
}
