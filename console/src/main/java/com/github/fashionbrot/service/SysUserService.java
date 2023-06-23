package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysUserEntity;
import com.github.fashionbrot.request.SysUserRequest;

/**
 * 系统用户表
 *
 * @author fashionbrot
 */
public interface SysUserService extends IService<SysUserEntity> {

    Object pageReq(SysUserRequest req);


    void updatePwd(String oldPassword, String newPassword);

    Object login(String account, String password);

    void add(SysUserEntity entity);

    void edit(SysUserEntity entity);

    void register(SysUserEntity entity);
}