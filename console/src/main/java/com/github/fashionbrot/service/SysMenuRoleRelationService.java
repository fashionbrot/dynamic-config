package com.github.fashionbrot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysMenuRoleRelationEntity;
import com.github.fashionbrot.request.SysMenuRoleRelationRequest;

/**
 * 菜单-角色关系表
 *
 * @author fashionbrot
 */
public interface SysMenuRoleRelationService  extends IService<SysMenuRoleRelationEntity> {

    Object pageReq(SysMenuRoleRelationRequest req);

}