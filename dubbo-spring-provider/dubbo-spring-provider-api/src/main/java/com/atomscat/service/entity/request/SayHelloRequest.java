package com.atomscat.service.entity.request;

import com.atomscat.service.entity.request.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "SayHelloRequest---传递参数")
public class SayHelloRequest extends BaseRequest {

    @ApiModelProperty("参数:name")
    String name;

    @ApiModelProperty("参数:bookBeanList")
    List<BookBean> bookBeanList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookBean> getBookBeanList() {
        return bookBeanList;
    }

    public void setBookBeanList(List<BookBean> bookBeanList) {
        this.bookBeanList = bookBeanList;
    }
}
