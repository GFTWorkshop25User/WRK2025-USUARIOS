package com.gft.user.infrastructure.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class RequestLoggingFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(logger.isInfoEnabled()) {
            logger.info("""
                    HTTP REQUEST
                    Request: {} {}
                    IP: {}
                    User-Agent: {}
                    """, request.getMethod(), request.getRequestURI(), request.getRemoteAddr(), request.getHeader("User-Agent"));
        }

        filterChain.doFilter(servletRequest, servletResponse);

        long endTime = System.currentTimeMillis();

        long elapsedTimeInMilliseconds = endTime - startTime;

        logger.info("""
                HTTP RESPONSE
                Response: {}
                Elapsed time: {}ms
                """, response.getStatus(), elapsedTimeInMilliseconds);

    }
}
