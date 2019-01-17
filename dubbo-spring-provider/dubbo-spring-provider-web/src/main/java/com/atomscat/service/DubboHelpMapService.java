package com.atomscat.service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DubboHelpMapService {

    public JSONObject getHelpByKey(JSONObject jsonObject, String key){
        key = "/" + key;
        JSONObject object = (JSONObject)((JSONObject) jsonObject.get("paths")).get(key);
        return object;
    }

}
