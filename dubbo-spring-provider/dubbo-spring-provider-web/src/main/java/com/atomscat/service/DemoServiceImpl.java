package com.atomscat.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atomscat.service.entity.request.BookBean;
import com.atomscat.service.entity.request.SayHelloRequest;
import com.atomscat.service.entity.request.base.CommonRequest;
import com.atomscat.service.entity.response.SayHelloResponse;
import com.atomscat.service.entity.response.base.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {
    private static Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);


    @Value("${dubbo.application.name}")
    private String serviceName;

    public SayHelloResponse sayHello(String name) {
        SayHelloResponse sayHelloResponse = new SayHelloResponse();
        sayHelloResponse.setMsg(String.format("[%s] : Hello, %s", serviceName, name));
        return sayHelloResponse;
    }

    @Override
    public SayHelloResponse sayHello(SayHelloRequest sayHelloRequest) {
        SayHelloResponse sayHelloResponse = new SayHelloResponse();
        sayHelloResponse.setMsg(String.format("[%s] : Hello, %s", serviceName, sayHelloRequest.getName()));
        return sayHelloResponse;
    }

    @Override
    public CommonResponse<SayHelloResponse> sayHello(CommonRequest<SayHelloRequest> sayHelloRequestCommonRequest) {
        logger.info(JSON.toJSONString(sayHelloRequestCommonRequest));
        CommonResponse<SayHelloResponse> sayHelloResponseCommonResponse = new  CommonResponse<SayHelloResponse>();
        sayHelloResponseCommonResponse.setCode("000000");
        sayHelloResponseCommonResponse.setContent("ok");
        SayHelloResponse sayHelloResponse = new SayHelloResponse();
        List<BookBean> bookBeanList = sayHelloRequestCommonRequest.getRequest().getBookBeanList();
        sayHelloResponse.setMsg(String.format("[%s] : Hello, %s", serviceName, sayHelloRequestCommonRequest.getRequest().getName()) + "bookBeanList: "+ bookBeanList.size());
        sayHelloResponseCommonResponse.setResponse(sayHelloResponse);
        return sayHelloResponseCommonResponse;
    }
}
