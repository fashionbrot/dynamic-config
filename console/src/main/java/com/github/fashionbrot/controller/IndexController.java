package com.github.fashionbrot.controller;



import com.github.fashionbrot.entity.SysMenuEntity;
import com.github.fashionbrot.entity.SysUserEntity;
import com.github.fashionbrot.exception.DynamicConfigException;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.service.SysMenuService;
import com.github.fashionbrot.service.SysUserService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.fashionbrot.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    final SysUserService sysUserService;
    final UserLoginService userLoginService;
    final SysMenuService sysMenuService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/index")
    public String index(ModelMap mmap){
        try {
            setModelMap(mmap);
        }catch (Exception e){
            log.error("index error",e);
            if (e instanceof DynamicConfigException){
                DynamicConfigException m = (DynamicConfigException) e;
                if (m.getCode()==11){
                    return "login";
                }
            }
        }
        return "index";
    }

    @GetMapping("/index-topnav")
    public String indexTopnav(ModelMap mmap){
        try {
            setModelMap(mmap);
        }catch (Exception e){
            log.error("indexTopnav error",e);
            if (e instanceof DynamicConfigException){
                DynamicConfigException m = (DynamicConfigException) e;
                if (m.getCode()==11){
                    return "login";
                }
            }
        }

        return "index-topnav";
    }


    private void setModelMap(ModelMap mmap) {
        List<SysMenuEntity> menus =sysMenuService.loadAllMenu();
        mmap.put("menus", menus);
        String userName="";
        LoginModel login = userLoginService.getLogin();
        if (login!=null){
            SysUserEntity byId = sysUserService.getById(login.getUserId());
            if (byId!=null){
                userName = byId.getUserName();
            }
        }
        mmap.put("userName", userName);
    }

    @GetMapping("/build")
    public String bulid(){
        return "build/build";
    }


    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin() {
        return "skin";
    }

    // 切换菜单
    @GetMapping("/system/menuStyle/{style}")
    public void menuStyle(@PathVariable String style, HttpServletResponse response) {
        CookieUtil.setCookie(response, "nav-style", style);
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        return "main";
    }





}
