package com.atomscat.service.entity.response;

import com.atomscat.service.entity.response.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "SayHelloResponse---返回实体")
public class SayHelloResponse extends BaseResponse {

    @ApiModelProperty("返回:msg")
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
