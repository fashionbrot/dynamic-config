package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.EnvEntity;
import com.github.fashionbrot.mapper.EnvMapper;
import com.github.fashionbrot.request.EnvRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.EnvService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 环境表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
@RequiredArgsConstructor
public class EnvServiceImpl  extends ServiceImpl<EnvMapper, EnvEntity> implements EnvService {

    final EnvMapper envMapper;

    @Override
    public Object pageReq(EnvRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<EnvEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<EnvEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}