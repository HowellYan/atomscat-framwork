package com.atomscat.service;

import com.atomscat.service.entity.request.BookBean;
import com.atomscat.service.entity.request.SayHelloRequest;
import com.atomscat.service.entity.request.base.CommonRequest;
import com.atomscat.service.entity.response.SayHelloResponse;
import com.atomscat.service.entity.response.base.CommonResponse;
import io.swagger.annotations.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;

@Path("/")
@Api(value = "/", description = "Operations about pets")
@Produces({"application/json"})
public interface DemoService {
    @POST
    @Path("sayHello1")
    @ApiOperation(value = "测试sayHello1接口")
    @ApiResponses(value = {})
    SayHelloResponse sayHello(@ApiParam String name);

    @POST
    @Path("sayHello2")
    @ApiOperation(value = "测试sayHello2接口")
    @ApiResponses(value = {})
    SayHelloResponse sayHello(@ApiParam  SayHelloRequest sayHelloRequest);

    @POST
    @Path("sayHello3")
    @ApiOperation(value = "Find pet by ID")
    @ApiResponses(value = {})
    CommonResponse<SayHelloResponse> sayHello(@ApiParam CommonRequest<SayHelloRequest> sayHelloRequestCommonRequest);

    @POST
    @Path("sayHello4")
    @ApiOperation(value = "Find pet by ID")
    @ApiResponses(value = {})
    CommonResponse<SayHelloResponse> sayHello(@ApiParam CommonRequest<SayHelloRequest> sayHelloRequestCommonRequest, CommonRequest<List<BookBean>> listCommonRequest);

    @POST
    @Path("getBook")
    @ApiOperation(value = "Find pet by ID")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid ID supplied"), @ApiResponse(code = 404, message = "Pet not found")})
    CommonResponse<SayHelloResponse> getBook(@ApiParam CommonRequest<Map<String, BookBean>> sayHelloRequestCommonRequest);

}
