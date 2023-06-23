package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SysMenuRoleRelationEntity;
import com.github.fashionbrot.mapper.SysMenuRoleRelationMapper;
import com.github.fashionbrot.request.SysMenuRoleRelationRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.SysMenuRoleRelationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单-角色关系表
 *
 * @author fashionbrot
 */
@Service
public class SysMenuRoleRelationServiceImpl  extends ServiceImpl<SysMenuRoleRelationMapper, SysMenuRoleRelationEntity> implements SysMenuRoleRelationService {

    @Autowired
    private SysMenuRoleRelationMapper sysMenuRoleRelationMapper;

    @Override
    public Object pageReq(SysMenuRoleRelationRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<SysMenuRoleRelationEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<SysMenuRoleRelationEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}