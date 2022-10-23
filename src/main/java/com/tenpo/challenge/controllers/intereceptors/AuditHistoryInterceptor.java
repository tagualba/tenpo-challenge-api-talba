package com.tenpo.challenge.controllers.intereceptors;

import com.tenpo.challenge.services.AuditHistoryService;
import com.tenpo.challenge.utils.Globals;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditHistoryInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuditHistoryInterceptor.class);

    @Autowired
    private AuditHistoryService auditHistoryService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {
        log.info(String.format("Operation:%s - Request: %s - Response: %s ", Globals.RESPONSE.get(), Globals.REQUEST.get(), Globals.OPERATION.get()));
        auditHistoryService.saveOperation(Globals.OPERATION.get(), Globals.REQUEST.get(), Globals.RESPONSE.get());
    }

}
