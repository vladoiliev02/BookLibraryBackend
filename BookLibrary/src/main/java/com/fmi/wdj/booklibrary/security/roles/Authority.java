package com.fmi.wdj.booklibrary.security.roles;

import lombok.Getter;

@Getter
public enum Authority {
    BOOK_WRITE("book:read"),
    BOOK_READ("book:write"),
    ALL_USER_READ("allUser:write"),
    ALL_USER_WRITE("allUser:write"),
    SELF_USER_READ("selfUser:write"),
    SELF_USER_WRITE("selfUser:write");

    private final String authority;

    private Authority(String authority) {
        this.authority = authority;
    }
}
