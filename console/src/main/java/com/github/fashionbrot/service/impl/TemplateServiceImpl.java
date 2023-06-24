package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.TemplateEntity;
import com.github.fashionbrot.exception.DynamicConfigException;
import com.github.fashionbrot.mapper.TemplateMapper;
import com.github.fashionbrot.request.TemplateRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.TemplateService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl  extends ServiceImpl<TemplateMapper, TemplateEntity> implements TemplateService {


    final TemplateMapper templateMapper;

    @Override
    public Object pageReq(TemplateRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(req.getAppCode())){
            map.put("app_code",req.getAppCode());
        }
        List<TemplateEntity> listByMap = templateMapper.selectByMap(map);

        return PageResponse.<TemplateEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    public void add(TemplateEntity entity) {
        QueryWrapper q = new QueryWrapper<>().eq("app_code",entity.getAppCode()).eq("template_key",entity.getTemplateKey());
        if(templateMapper.selectCount(q)>0){
            throw new DynamicConfigException("["+entity.getTemplateKey()+"]已存在，请重新输入");
        }
        templateMapper.insert(entity);
    }

    @Override
    public void edit(TemplateEntity entity) {
        QueryWrapper q = new QueryWrapper<>().eq("app_code",entity.getAppCode()).eq("template_key",entity.getTemplateKey());
        TemplateEntity temp = templateMapper.selectOne(q);
        if(temp!=null && entity.getId().longValue()!=temp.getId().longValue()){
            throw new DynamicConfigException("["+entity.getTemplateKey()+"]已存在，请重新输入");
        }
        templateMapper.updateById(entity);
    }

}