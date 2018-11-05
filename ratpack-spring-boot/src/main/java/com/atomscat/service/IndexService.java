package com.atomscat.service;

import org.json.JSONObject;

public interface IndexService {
    String message();
    String get(JSONObject jsonObject);
    String post(JSONObject jsonObject);
}
