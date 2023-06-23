package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysMenuEntity;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.request.SysMenuRequest;


import java.util.List;

/**
 * 菜单表
 *
 * @author fashionbrot
 */
public interface SysMenuService  extends IService<SysMenuEntity> {

    Object pageReq(SysMenuRequest req);

    boolean checkPermission(Object handler, LoginModel model);

    List<SysMenuEntity> loadAllMenu();

    List<SysMenuEntity> loadMenuAll(Long roleId, Integer isShowCode);
}