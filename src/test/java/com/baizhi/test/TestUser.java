package com.baizhi.test;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.MapVo;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUser {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Test
    public void test() {
        Integer count = userDao.findUsers("男", 1);
        System.out.println(count);
    }

    @Test
    public void testm() {
        List<MapVo> maps = userDao.findMap();
        System.out.println(maps);
    }

    @Test
    public void ts() {
        User user = new User();
        user.setPhone("15737496971");
        user.setPassword("123456");
        User login = userService.login(user);
        System.out.println(login);
    }

    @Test
    public void fin() {
        List<User> users = userDao.selectAll();
        System.out.println(users.size());
        List<User> users1 = users.subList(0, 4);
        System.out.println(users1.size());
    }
}
