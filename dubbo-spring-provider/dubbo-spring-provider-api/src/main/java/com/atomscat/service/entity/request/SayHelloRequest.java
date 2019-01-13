package com.atomscat.service.entity.request;

import com.atomscat.service.entity.request.base.BaseRequest;

import java.util.List;

public class SayHelloRequest extends BaseRequest {
    String name;

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
