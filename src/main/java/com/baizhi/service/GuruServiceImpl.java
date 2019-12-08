package com.baizhi.service;

import com.baizhi.annotation.AddRedisCacheAnnotation;
import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Guru;
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
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;

    //添加上师
    public Map add(Guru guru) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString();
        guru.setId(s);
        guruDao.insert(guru);
        map.put("id", s);
        map.put("status", "addok");
        return map;
    }

    //修改上师
    public Map update(Guru guru) {
        Map map = new HashMap();
        guruDao.updateByPrimaryKeySelective(guru);
        map.put("status", "updateok");
        return map;
    }

    //删除上师根据ID
    public Map delete(String id) {
        Map map = new HashMap();
        guruDao.deleteByPrimaryKey(id);
        map.put("status", "delok");
        return map;
    }

    //查询所有上师
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Guru> gurus = guruDao.selectByRowBounds(new Guru(), new RowBounds((page - 1) * rows, rows));
        int records = guruDao.selectCount(new Guru());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", gurus);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    //查询所有上师
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddRedisCacheAnnotation
    public List<Guru> findAll() {
        System.out.println("执行了查询所有方法");
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }
}
