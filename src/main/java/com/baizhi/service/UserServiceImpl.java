package com.baizhi.service;

import com.baizhi.annotation.GoeasyAnnotation;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.MapVo;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    //登录
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {
        User login = null;
        try {
            //System.out.println(admin.getUsername());
            User u = new User();
            u.setPhone(user.getPhone());
            login = userDao.selectOne(u);
            //System.out.println(login);
            if (login == null) throw new RuntimeException("该用户不存在或者不是管理员");
            if (!login.getPassword().equals(user.getPassword())) throw new RuntimeException("密码输入错误！~");
            return login;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //添加用户
    @GoeasyAnnotation
    public Map add(User user) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString();
        user.setId(s);
        userDao.insert(user);
        map.put("id", s);
        map.put("status", "addok");
        return map;
    }

    //修改用户
    public Map update(User user) {
        Map map = new HashMap();
        userDao.updateByPrimaryKeySelective(user);
        map.put("status", "updateok");
        return map;
    }

    @GoeasyAnnotation
    //删除用户根据ID
    public Map delete(String id) {
        Map map = new HashMap();
        userDao.deleteByPrimaryKey(id);
        map.put("status", "delok");
        return map;
    }

    //查询所有用户
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<User> users = userDao.selectByRowBounds(new User(), new RowBounds((page - 1) * rows, rows));
        int records = userDao.selectCount(new User());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", users);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    //查询一个对象
    public User findUser(String id) {
        return userDao.selectOne(new User().setId(id));
    }

    //用户注册趋势
    public Integer findUsers(String sex, Integer day) {
        return userDao.findUsers(sex, day);
    }

    //用户分布图
    public List<MapVo> findMap() {
        return userDao.findMap();
    }
}
