package com.atomscat.service;

import com.atomscat.service.entity.request.BookBean;
import com.atomscat.service.entity.request.SayHelloRequest;
import com.atomscat.service.entity.request.base.CommonRequest;
import com.atomscat.service.entity.response.SayHelloResponse;
import com.atomscat.service.entity.response.base.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description = "demo接口")
public interface DemoService {
    SayHelloResponse sayHello(String name);

    @ApiOperation(value = "sayHello2")
    SayHelloResponse sayHello(SayHelloRequest sayHelloRequest);

    CommonResponse<SayHelloResponse> sayHello(CommonRequest<SayHelloRequest> sayHelloRequestCommonRequest);
    CommonResponse<SayHelloResponse> sayHello(CommonRequest<SayHelloRequest> sayHelloRequestCommonRequest, CommonRequest<List<BookBean>> listCommonRequest);
    CommonResponse<SayHelloResponse> getBook(CommonRequest<Map<String, BookBean>> sayHelloRequestCommonRequest);

}
