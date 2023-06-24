package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.PropertyEntity;
import com.github.fashionbrot.mapper.PropertyMapper;
import com.github.fashionbrot.request.CopyPropertyRequest;
import com.github.fashionbrot.request.PropertyRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.PropertyService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 属性表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
@RequiredArgsConstructor
public class PropertyServiceImpl  extends ServiceImpl<PropertyMapper, PropertyEntity> implements PropertyService {

    final PropertyMapper propertyMapper;

    @Override
    public Object pageReq(PropertyRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        QueryWrapper q =new QueryWrapper();
        if (StringUtils.isNotEmpty(req.getAppCode())){
            q.eq("app_code",req.getAppCode());
            if (StringUtils.isNotEmpty(req.getTemplateKey())){
                q.eq("template_key",req.getTemplateKey());
            }
        }
        q.orderByDesc("priority");

        List<PropertyEntity> listByMap = propertyMapper.selectList(q);

        return PageResponse.<PropertyEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    public Object queryList(PropertyRequest req) {
        QueryWrapper q =new QueryWrapper();
        if (StringUtils.isNotEmpty(req.getAppCode())){
            q.eq("app_code",req.getAppCode());
            if (StringUtils.isNotEmpty(req.getTemplateKey())){
                q.eq("template_key",req.getTemplateKey());
            }
        }
        if (req.getShowTable()!=null){
            q.eq("show_table",req.getShowTable());
        }
        q.orderByDesc("priority");
        return propertyMapper.selectList(q);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyProperty(CopyPropertyRequest req) {
        List<Integer> ids = Arrays.stream(req.getSourceIds().split(",")).map(m->Integer.valueOf(m)).collect(Collectors.toList());
        List<PropertyEntity> propertyEntities = propertyMapper.selectBatchIds(ids);
        if (CollectionUtils.isNotEmpty(propertyEntities)){
            for(PropertyEntity p: propertyEntities){

                QueryWrapper q=new QueryWrapper();
                q.eq("app_code",req.getTargetAppCode());
                q.eq("template_key",req.getTargetTemplateKey());
                q.eq("property_key",p.getPropertyKey());
                if (propertyMapper.selectCount(q)>0){
                    continue;
                }
                p.setId(null);
                p.setAppCode(req.getTargetAppCode());
                p.setTemplateKey(req.getTargetTemplateKey());
                propertyMapper.insert(p);
            }
        }
    }
}