package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
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
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    //添加轮播图
    public Map add(Banner banner) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString();
        banner.setId(s);
        bannerDao.insert(banner);
        map.put("id", s);
        map.put("status", "addok");
        return map;
    }

    //修改轮播图
    public Map update(Banner banner) {
        Map map = new HashMap();
        bannerDao.updateByPrimaryKeySelective(banner);
        map.put("status", "updateok");
        return map;
    }

    //删除轮播图根据ID
    public Map delete(String id) {
        Map map = new HashMap();
        bannerDao.deleteByPrimaryKey(id);
        map.put("status", "delok");
        return map;
    }

    //查询所有轮播图
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), new RowBounds((page - 1) * rows, rows));
        int records = bannerDao.selectCount(new Banner());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", banners);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    //查询所有轮播图不分页
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> findBanners() {
        return bannerDao.selectAll();
    }
}
