package com.atomscat.service.impl;

import com.atomscat.service.IndexService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {
    @Override
    public String message() {
        return "World!";
    }

    @Override
    public String get(JSONObject jsonObject) {
        return jsonObject.toString();
    }

    @Override
    public String post(JSONObject jsonObject) {
        return jsonObject.toString();
    }
}
