package com.tenpo.challenge.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GlobalsUtilTest {

    @Test
    public void globalSetGetOperationOk(){
        String operation = "operationx";
        GlobalsUtil.setOperation(operation);

        assertTrue(Globals.OPERATION.get() == operation);
    }


    @Test
    public void globalSetGetOk(){
        String request = "{request}";
        GlobalsUtil.setRequest(request);

        assertTrue(Globals.REQUEST.get() == request);
    }

    @Test
    public void globalSetGetResponseOk(){
        String response = "{response}";
        GlobalsUtil.setResponse(response);

        assertTrue(Globals.RESPONSE.get() == response);
    }

}
