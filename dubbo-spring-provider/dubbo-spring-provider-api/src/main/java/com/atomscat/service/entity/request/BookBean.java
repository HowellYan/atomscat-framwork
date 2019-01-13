package com.atomscat.service.entity.request;

import com.atomscat.service.entity.request.base.BaseRequest;

public class BookBean extends BaseRequest {
    String bookId;
    String bookName;
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
