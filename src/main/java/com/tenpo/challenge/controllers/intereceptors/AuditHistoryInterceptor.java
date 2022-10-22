package com.tenpo.challenge.controllers.intereceptors;

import com.tenpo.challenge.services.AuditHistoryService;
import com.tenpo.challenge.utils.Globals;
import com.tenpo.challenge.utils.GlobalsUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditHistoryInterceptor implements HandlerInterceptor {

    @Autowired
    private AuditHistoryService auditHistoryService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {
        auditHistoryService.saveOperation(Globals.OPERATION.get(), Globals.REQUEST.get(), Globals.RESPONSE.get());
    }
}
