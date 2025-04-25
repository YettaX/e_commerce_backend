package com.example.e_commerce_backend.config;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 25/4/2025
 */

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);


        filterChain.doFilter(requestWrapper, response);
        logRequestDetails(requestWrapper);

        logResponseDetails(responseWrapper);

        responseWrapper.copyBodyToResponse();
    }


    private void logRequestDetails(ContentCachingRequestWrapper request) throws IOException{
        System.out.println("\n Incoming Request");
        System.out.println("Method: " + request.getMethod());
        System.out.println("URL: " + request.getRequestURI());

        System.out.println("------- Headers ---------");
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            Enumeration<String> values = request.getHeaders(name); // 支持重复值
            while (values.hasMoreElements()) {
                System.out.println(name + ": " + values.nextElement());
            }
        }


        System.out.println("-------- Parameters --------");
        request.getParameterMap().forEach((key, values) -> {
            System.out.println(key + ": " + Arrays.toString(values));
        });


        System.out.println("--------- JSON Body -------------");
        String encoding = request.getCharacterEncoding() != null ? request.getCharacterEncoding() : "UTF-8";
        String body = new String(request.getContentAsByteArray(), encoding);
        System.out.println(body);
        System.out.println("---------------------------------\n");

    }


    private void logResponseDetails(ContentCachingResponseWrapper responseWrapper) throws IOException{
        System.out.println("\n Outgoing Response");
        System.out.println("Status: " + responseWrapper.getStatus());


        System.out.println("----------- Response Headers ----------");
        for (String name: responseWrapper.getHeaderNames()) {
            System.out.println(name + ": " + responseWrapper.getHeader(name));
        }



        System.out.println("----------- Response Body -------------");
        String body = new String(responseWrapper.getContentAsByteArray());
        System.out.println(body);
        System.out.println("-----------------------------------------\n");

    }


}
