package com.tenpo.challenge.controllers.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.exceptions.ApiError;
import com.tenpo.challenge.statics.ErrorCode;
import com.tenpo.challenge.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@AllArgsConstructor
public class AuthorizationFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);

    private final String TOKEN_API_KEY_HEADER = "tokenApiKey";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        log.info(String.format("Event: AuthorizationFilter %s %s", httpRequest.getRequestURI(), httpRequest.getMethod()));

        if (!validateToken(httpRequest)) {
            httpResponse.setStatus(ErrorCode.INVALID_TOKEN.getHttpStatus().value());
            httpResponse.getWriter().write(objectMapper.writeValueAsString(ApiError.builder()
                    .error(ErrorCode.INVALID_TOKEN.getName())
                    .message(ErrorCode.INVALID_TOKEN.getMessage())
                    .status(ErrorCode.INVALID_TOKEN.getHttpStatus().value())
                    .errorCode(ErrorCode.INVALID_TOKEN.getCode())
                    .build()));
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Boolean validateToken(HttpServletRequest request) {
        String tokenApiKey = request.getHeader(TOKEN_API_KEY_HEADER);
        if (tokenApiKey == null || !jwtUtil.validate(tokenApiKey)) {
            return false;
        }
        return true;
    }

}
