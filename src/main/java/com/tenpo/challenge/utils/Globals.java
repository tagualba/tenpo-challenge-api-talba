package com.tenpo.challenge.utils;

public class Globals {

    public static ThreadLocal<String> OPERATION;

    public static ThreadLocal<String> REQUEST;

    public static ThreadLocal<String> RESPONSE;

    static {
        OPERATION = new ThreadLocal<>();
        REQUEST = new ThreadLocal<>();
        RESPONSE = new ThreadLocal<>();
    }

}
