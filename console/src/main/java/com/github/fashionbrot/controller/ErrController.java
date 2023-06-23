package com.github.fashionbrot.controller;

import com.github.fashionbrot.enums.RespEnum;
import com.github.fashionbrot.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrController implements ErrorController {



    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/401",produces = "text/html",method = {RequestMethod.POST,RequestMethod.GET})
    public String unauthorizedHtml(String requestUrl){
        request.setAttribute("requestUrl",requestUrl);
        return "error/unauth";
    }

    @RequestMapping(value = "/401",produces = "application/json",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Response unauthorizedJson(String requestUrl){
       return  Response.fail(RespEnum.NOT_PERMISSION_ERROR.getMsg());
    }




    @RequestMapping(value = "/error",produces = "text/html",method = {RequestMethod.POST,RequestMethod.GET})
    public String handleErrorHtml(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode!=null) {
            if (statusCode == 500) {
                return "error/500";
            } else if (statusCode == 404) {
                return "error/404";
            }
        }
        return "error/500";
    }

    @RequestMapping(value = "/error",produces = "application/json",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Response handleErrorJson(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode!=null){
            if(statusCode == 500){
                return Response.fail("请求异常",500);
            }else if(statusCode == 404){
                return Response.fail("接口不存在",404);
            }
        }
        return Response.fail("未知异常",statusCode);
    }

}
