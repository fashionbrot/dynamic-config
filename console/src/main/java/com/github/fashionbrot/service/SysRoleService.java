package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysRoleEntity;
import com.github.fashionbrot.request.SysRoleRequest;


/**
 * 角色表
 *
 * @author fashionbrot
 */
public interface SysRoleService  extends IService<SysRoleEntity> {

    Object pageReq(SysRoleRequest req);

    void add(SysRoleEntity entity);

    void edit(SysRoleEntity entity);
}