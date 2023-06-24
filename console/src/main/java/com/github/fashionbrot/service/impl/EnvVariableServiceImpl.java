package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.EnvVariableEntity;
import com.github.fashionbrot.entity.EnvVariableRelationEntity;
import com.github.fashionbrot.exception.DynamicConfigException;
import com.github.fashionbrot.mapper.EnvVariableMapper;
import com.github.fashionbrot.mapper.EnvVariableRelationMapper;
import com.github.fashionbrot.request.EnvVariableRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.EnvVariableService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 常量表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
@RequiredArgsConstructor
public class EnvVariableServiceImpl  extends ServiceImpl<EnvVariableMapper, EnvVariableEntity> implements EnvVariableService {


    final EnvVariableRelationMapper envVariableRelationMapper;
    final EnvVariableMapper envVariableMapper;

    @Override
    public Object pageReq(EnvVariableRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<EnvVariableEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<EnvVariableEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(EnvVariableEntity entity) {
        Long count = envVariableMapper.selectCount(new QueryWrapper<EnvVariableEntity>().eq("variable_key", entity.getVariableKey()));
        if (count>0){
            throw new DynamicConfigException(entity.getVariableKey()+"：已存在，请重新输入");
        }

        baseMapper.insert(entity);
        List<EnvVariableRelationEntity> relation = entity.getRelation();
        if (CollectionUtils.isNotEmpty(relation)){
            for(EnvVariableRelationEntity e :relation){
                envVariableRelationMapper.insert(e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(EnvVariableEntity entity) {
        EnvVariableEntity variable_key = baseMapper.selectOne(new QueryWrapper<EnvVariableEntity>().eq("variable_key", entity.getVariableKey()));
        if (variable_key!=null && variable_key.getId().longValue()!=entity.getId().longValue()){
            throw new DynamicConfigException(entity.getVariableKey()+"：已存在，请重新输入");
        }

        baseMapper.updateById(entity);
        envVariableRelationMapper.delete(new QueryWrapper<EnvVariableRelationEntity>().eq("variable_key",entity.getVariableKey()));
        List<EnvVariableRelationEntity> relation = entity.getRelation();
        if (CollectionUtils.isNotEmpty(relation)){
            for(EnvVariableRelationEntity e :relation){
                envVariableRelationMapper.insert(e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        EnvVariableEntity entity = baseMapper.selectById(id);
        if (entity!=null){
            //TODO 判断是否已使用，已使用不能删除
            baseMapper.deleteById(id);
            envVariableRelationMapper.delete(new QueryWrapper<EnvVariableRelationEntity>().eq("variable_key",entity.getVariableKey()));
        }

    }
}