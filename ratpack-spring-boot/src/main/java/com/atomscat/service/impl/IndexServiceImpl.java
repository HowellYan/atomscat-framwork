package com.atomscat.service.impl;

import com.atomscat.dao.mapper.AreaMapper;
import com.atomscat.entity.base.Area;
import com.atomscat.service.IndexService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.java.Log;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Log
public class IndexServiceImpl implements IndexService {

    @Resource
    private AreaMapper areaDao;

    @Override
    public String message() {
        return "World!";
    }

    @Override
    public String get(JSONObject jsonObject) {
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        List<Area> list = areaDao.selectList(queryWrapper);
        log.info("list size:" + list.size());
        return jsonObject.toString();
    }

    @Override
    public String post(JSONObject jsonObject) {
        return jsonObject.toString();
    }
}
