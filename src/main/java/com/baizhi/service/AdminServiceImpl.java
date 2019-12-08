package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin login(Admin admin) {
        Admin login = null;
        try {
            //System.out.println(admin.getUsername());
            Admin a = new Admin();
            a.setUsername(admin.getUsername());
            login = adminDao.selectOne(a);
            //System.out.println(login);
            if (login == null) throw new RuntimeException("该用户不存在或者不是管理员");
            if (!login.getPassword().equals(admin.getPassword())) throw new RuntimeException("密码输入错误！~");
            return login;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
