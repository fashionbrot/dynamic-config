package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SystemReleaseEntity;
import com.github.fashionbrot.mapper.SystemReleaseMapper;
import com.github.fashionbrot.request.SystemReleaseRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.SystemReleaseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统配置发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
@Service
@RequiredArgsConstructor
public class SystemReleaseServiceImpl  extends ServiceImpl<SystemReleaseMapper, SystemReleaseEntity> implements SystemReleaseService {

    final SystemReleaseMapper systemReleaseMapper;

    @Override
    public Object pageReq(SystemReleaseRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<SystemReleaseEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<SystemReleaseEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}