package com.baizhi.test;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAdminDao {
    @Autowired
    private AdminDao adminDao;

    @Test
    public void testSellectAll() {
        List<Admin> admins = adminDao.selectAll();
        admins.forEach(admin -> System.out.println(admin));
    }

    @Test
    public void tests() {
        Admin admin = new Admin();
        admin.setId("1");
        System.out.println(adminDao.selectOne(admin));
    }

    @Test
    public void testA() {
        Integer a = 0;
        Integer b = 2;
        System.out.println(adminDao.selectByRowBounds(new Admin(), new RowBounds(a, b)));
    }

    @Test
    public void testc() {
        //System.out.println(adminDao.selectCount(new Admin()));
        // adminDao.deleteByPrimaryKey("2");
        System.out.println(adminDao.selectByPrimaryKey("1"));
    }
}
