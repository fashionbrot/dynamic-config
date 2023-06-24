package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SystemConfigRoleRelationEntity;
import com.github.fashionbrot.mapper.SystemConfigRoleRelationMapper;
import com.github.fashionbrot.request.SystemConfigRoleRelationRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.SystemConfigRoleRelationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 应用系统配置-角色关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
@Service
@RequiredArgsConstructor
public class SystemConfigRoleRelationServiceImpl  extends ServiceImpl<SystemConfigRoleRelationMapper, SystemConfigRoleRelationEntity> implements SystemConfigRoleRelationService {

    final SystemConfigRoleRelationMapper systemConfigRoleRelationMapper;

    @Override
    public Object pageReq(SystemConfigRoleRelationRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<SystemConfigRoleRelationEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<SystemConfigRoleRelationEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}