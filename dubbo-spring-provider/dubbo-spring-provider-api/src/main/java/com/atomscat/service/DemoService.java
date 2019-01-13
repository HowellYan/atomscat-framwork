package com.atomscat.service;

import com.atomscat.service.entity.request.SayHelloRequest;
import com.atomscat.service.entity.request.base.CommonRequest;
import com.atomscat.service.entity.response.SayHelloResponse;
import com.atomscat.service.entity.response.base.CommonResponse;

public interface DemoService {
    SayHelloResponse sayHello(String name);
    SayHelloResponse sayHello(SayHelloRequest sayHelloRequest);
    CommonResponse<SayHelloResponse> sayHello(CommonRequest<SayHelloRequest> sayHelloRequestCommonRequest);
}
