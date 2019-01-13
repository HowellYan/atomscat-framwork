package com.atomscat.service.entity.response.base;

public class CommonResponse<T> extends BaseResponse {
    /**
     * 返回业务数据
     */
    private T response;

    /**
     * 响应编码
     */
    private String code;

    /**
     * 响应描述
     */
    private String content;


    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
