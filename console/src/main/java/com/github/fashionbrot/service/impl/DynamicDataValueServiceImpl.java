package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.DynamicDataValueEntity;
import com.github.fashionbrot.mapper.DynamicDataValueMapper;
import com.github.fashionbrot.request.DynamicDataValueRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.DynamicDataValueService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态配置数据表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Service
@RequiredArgsConstructor
public class DynamicDataValueServiceImpl  extends ServiceImpl<DynamicDataValueMapper, DynamicDataValueEntity> implements DynamicDataValueService {

    final DynamicDataValueMapper dynamicDataValueMapper;

    @Override
    public Object pageReq(DynamicDataValueRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<DynamicDataValueEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<DynamicDataValueEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}