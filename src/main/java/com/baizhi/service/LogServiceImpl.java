package com.baizhi.service;

import com.baizhi.dao.LogDao;
import com.baizhi.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;

    //添加日志文件
    public Map add(Log log) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString();
        log.setId(s);
        logDao.insert(log);
        map.put("id", s);
        map.put("status", "addok");
        return map;
    }
}
