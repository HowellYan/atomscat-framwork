package com.atomscat.service.entity.request;

import com.atomscat.service.entity.request.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "BookBean---传递参数")
public class BookBean extends BaseRequest {

    @ApiModelProperty("参数:bookId")
    String bookId;

    @ApiModelProperty("参数:bookName")
    String bookName;

    @ApiModelProperty("参数:bookPageSize")
    String bookPageSize;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPageSize() {
        return bookPageSize;
    }

    public void setBookPageSize(String bookPageSize) {
        this.bookPageSize = bookPageSize;
    }
}
