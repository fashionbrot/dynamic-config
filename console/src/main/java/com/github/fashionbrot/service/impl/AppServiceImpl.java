package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.AppEntity;
import com.github.fashionbrot.mapper.AppMapper;
import com.github.fashionbrot.request.AppRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.AppService;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 应用表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
@RequiredArgsConstructor
public class AppServiceImpl  extends ServiceImpl<AppMapper, AppEntity> implements AppService {

    final AppMapper appMapper;

    @Override
    public Object pageReq(AppRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<AppEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<AppEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}