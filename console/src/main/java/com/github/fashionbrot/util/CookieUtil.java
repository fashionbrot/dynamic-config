package com.github.fashionbrot.util;


import com.github.fashionbrot.consts.GlobalConst;
import com.github.fashionbrot.model.LoginModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;


/**
 * Cookie 工具类
 */
public final class CookieUtil {

    private final static String REALNAME = "REAL_NAME";
    private final static String ROLENAME = "ROLE_NAME";



    /**
     * 增加cookie
     * @param response          response
     * @param responseCookie    responseCookie
     */
    public static void addCookie(HttpServletResponse response , ResponseCookie responseCookie){
        if (responseCookie!=null){
            response.addHeader(ResponseCookie.SET_COOKIE,responseCookie.toString());
        }
    }

    /**
     * 增加cookie
     * @param response          response
     * @param responseCookie    responseCookie list
     */
    public static void addCookie(HttpServletResponse response , List<ResponseCookie> responseCookie){
        if (ObjectUtil.isNotEmpty(responseCookie)){
            responseCookie.forEach(c->{
                if (c!=null){
                    response.addHeader(ResponseCookie.SET_COOKIE,c.toString());
                }
            });
        }
    }

    /**
     * 增加cookie
     * @param response          response
     * @param responseCookie    responseCookie[]
     */
    public static void addCookie(HttpServletResponse response , ResponseCookie... responseCookie){
        if (ObjectUtil.isNotEmpty(responseCookie)){
            for (int i = 0; i < responseCookie.length; i++) {
                if (responseCookie[i]!=null) {
                    response.addHeader(ResponseCookie.SET_COOKIE, responseCookie[i].toString());
                }
            }
        }
    }

    /**
     * 设置 Cookie（生成时间为1天）
     *
     * @param name  名称
     * @param value 值
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, 60 * 60 * 24);
    }

    /**
     * 设置 Cookie
     *
     * @param name   名称
     * @param value  值
     * @param maxAge 生存时间（单位秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, "/", maxAge);
    }

    /**
     * 设置 Cookie
     *
     * @param name   名称
     * @param value  值
     * @param maxAge 生存时间（单位秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        try {
            cookie.setValue(URLEncoder.encode(value, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
    }


    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String realName, String roleName, String token) {
        setCookie(request, response, REALNAME, realName, 30 * 60, true, false);
        setCookie(request, response, ROLENAME, roleName, 30 * 60, true, false);
        setCookie(request, response, GlobalConst.AUTH_KEY, token, 30 * 60, true, true);
    }

    /**
     * 重写 Cookie的值,
     *
     * @param request
     * @param response
     */
    public static void rewriteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList != null) {
            String token = getCookieValue(cookieList, GlobalConst.AUTH_KEY, true);

            if (ObjectUtil.isNotEmpty(token)) {
                LoginModel model = JwtTokenUtil.getLogin(token);
                if (model != null) {
                    token = JwtTokenUtil.createToken(model.getUserId(), model.getUserName(), model.getRoleId(), model.getRoleName(), model.isSuperAdmin(),model.getCompanyId());
                    if (ObjectUtil.isNotEmpty(token)) {
                        setCookie(request, response, model.getUserName(),  model.getRoleName(), token);
                    }
                }
            }
        }
    }


    public static String getCookieValue(Cookie[] cookieList, String cookieName, boolean isDecoder) {
        if (cookieList == null || cookieList.length == 0) {
            return "";
        }
        for (int i = 0; i < cookieList.length; i++) {
            if (cookieList[i].getName().equals(cookieName)) {
                if (isDecoder) {
                    try {
                        return URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    return cookieList[i].getValue();
                }
            }
        }
        return "";
    }

    /**
     * 得到Cookie的值,
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                    } else {
                        retValue = cookieList[i].getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }


    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge, boolean isEncode, boolean isHttpOnly) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxAge, isEncode, isHttpOnly);
    }


    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        doSetCookie(request, response, GlobalConst.AUTH_KEY, "", -1, false, false);
        doSetCookie(request, response, REALNAME, "", -1, false, false);
        doSetCookie(request, response, ROLENAME, "", -1, false, false);
    }


    /**
     * 设置Cookie的值，并使其在指定时间内生效
     *
     * @param cookieMaxAge cookie生效的最大秒数
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
                                          String cookieName, String cookieValue, int cookieMaxAge, boolean isEncode, boolean isHttpOnly) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else if (isEncode) {
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxAge > 0) {
                cookie.setMaxAge(cookieMaxAge);
            }
            if (null != request) {// 设置域名的cookie
                String domainName = request.getServerName();
                if (ObjectUtil.isNotEmpty(domainName)) {
                    cookie.setDomain(domainName);
                }
            }
            cookie.setHttpOnly(isHttpOnly);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}