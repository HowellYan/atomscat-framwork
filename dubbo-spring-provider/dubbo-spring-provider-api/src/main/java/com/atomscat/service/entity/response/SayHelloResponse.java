package com.atomscat.service.entity.response;

import com.atomscat.service.entity.response.base.BaseResponse;

public class SayHelloResponse extends BaseResponse {
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
