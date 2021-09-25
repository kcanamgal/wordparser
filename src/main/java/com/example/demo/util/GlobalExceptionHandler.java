package com.example.demo.util;

import com.example.demo.VO.RestResult;
import com.example.demo.exceptions.RequestParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestParamException.class)
    public RestResult handleException(RequestParamException t, HttpServletRequest httpServletRequest) {
        return RestResult.failure(t.getMessage());
    }
}
