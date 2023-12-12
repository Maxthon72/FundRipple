package com.fundripple.api.config;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Log the Request Details
        logger.info("Incoming request: " + request.getMethod() + " " + request.getRequestURI());
        logger.info("Request Origin: " + request.getHeader("Origin"));

        // Continue with the filter chain
        filterChain.doFilter(request, response);

        // Log the Response Details
        logger.info("Response Headers: ");
        response.getHeaderNames().forEach(headerName ->
                logger.info(headerName + ": " + response.getHeader(headerName))
        );
    }
}
