package com.tenpo.challenge.utils;

public class GlobalsUtil {

    public static void setOperation(String operation) {
        Globals.OPERATION.set(operation);
    }

    public static void setRequest(String request) {
        Globals.REQUEST.set(request);
    }

    public static void setResponse(String response) {
        Globals.RESPONSE.set(response);
    }

}
