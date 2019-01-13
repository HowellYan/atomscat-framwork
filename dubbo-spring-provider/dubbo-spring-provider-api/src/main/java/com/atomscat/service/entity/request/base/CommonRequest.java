package com.atomscat.service.entity.request.base;

public class CommonRequest<T> extends BaseRequest {
    /**
     * 具体的请求对象
     */
    private T request;

    /**
     * 应用模块名称
     */
    private String appName;

    /**
     * 渠道号-前端入口进来
     */
    private String channelCode;

    /**
     * 请求流水号
     */
    private String traceId;

    /**
     * 操作级别
     */
    private String operLevel;


    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getOperLevel() {
        return operLevel;
    }

    public void setOperLevel(String operLevel) {
        this.operLevel = operLevel;
    }
}
