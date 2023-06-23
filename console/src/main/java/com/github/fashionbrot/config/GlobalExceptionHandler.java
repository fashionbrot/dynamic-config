package com.github.fashionbrot.config;


import com.github.fashionbrot.annotation.ApiTag;
import com.github.fashionbrot.enums.RespEnum;
import com.github.fashionbrot.exception.DynamicConfigException;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.validated.constraint.MarsViolation;
import com.github.fashionbrot.validated.exception.ValidatedException;
import com.github.fashionbrot.validated.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(DynamicConfigException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response marsException(DynamicConfigException e) {
        return Response.fail(e.getMsg(), e.getCode());
    }


    @ExceptionHandler(Exception.class)
    public Object globalException(HttpServletRequest request, HandlerMethod handlerMethod, Exception ex) {
        log.error("exception error:{}",ex);

        ApiTag apiTag=  handlerMethod.getBeanType().getAnnotation(ApiTag.class);
        if(apiTag!=null || isAjax(request)){
            return Response.fail(RespEnum.FAIL.getMsg());
        }else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/error/500");
            modelAndView.addObject("errorMsg",ex.getMessage());
            return modelAndView;
        }
    }


    public boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }


    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response ValidatedException(ValidatedException e) {
        List<MarsViolation> violations = e.getViolations();
        String msg = e.getMsg();
        if (ObjectUtil.isNotEmpty(violations)) {
            if (violations.size() == 1) {
                msg = violations.get(0).getMsg();
            } else {
                msg = violations.stream().map(m-> m.getMsg()).collect(Collectors.joining(","));
            }
        }
        return Response.fail(msg);
    }



}
