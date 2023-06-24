package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.DynamicDataEntity;
import com.github.fashionbrot.entity.DynamicDataReleaseEntity;
import com.github.fashionbrot.enums.ReleaseTypeEnum;
import com.github.fashionbrot.exception.DynamicConfigException;
import com.github.fashionbrot.mapper.DynamicDataMapper;
import com.github.fashionbrot.mapper.DynamicDataReleaseMapper;
import com.github.fashionbrot.mapper.DynamicDataValueMapper;
import com.github.fashionbrot.request.DynamicDataReleaseRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.DynamicDataReleaseService;
import com.github.fashionbrot.service.DynamicDataService;
import com.github.fashionbrot.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


/**
 * 配置数据发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@SuppressWarnings("ALL")
@Service
@RequiredArgsConstructor
public class DynamicDataReleaseServiceImpl  extends ServiceImpl<DynamicDataReleaseMapper, DynamicDataReleaseEntity> implements DynamicDataReleaseService {

    final DynamicDataMapper dynamicDataMapper;
    final DynamicDataValueMapper dynamicDataValueMapper;
    final DynamicDataService dynamicDataService;


    @Override
    public Object pageReq(DynamicDataReleaseRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<DynamicDataReleaseEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<DynamicDataReleaseEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void release(DynamicDataReleaseEntity req) {
        QueryWrapper<DynamicDataEntity> q =new QueryWrapper();
        q.select("id,template_key");
        q.eq("env_code",req.getEnvCode());
        q.eq("app_code",req.getAppCode());
        if (ObjectUtil.isNotEmpty(req.getTemplateKeys())){
            q.eq("template_key",req.getTemplateKeys());
        }
        q.in("release_type", Arrays.asList(4,6,7,8));
        List<DynamicDataEntity> list = dynamicDataMapper.selectList(q);

        List<String> templateKeys = new ArrayList<>();
        List<Long> dataIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            for(DynamicDataEntity d: list){
                templateKeys.add(d.getTemplateKey());
                dataIds.add(d.getId());
            }
        }

        if (CollectionUtils.isEmpty(templateKeys)){
            throw new DynamicConfigException("没有要发布的模板");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("releaseType",ReleaseTypeEnum.RELEASE.getCode());
        map.put("dataIds",dataIds);
        dynamicDataMapper.updateRelease(map);
        dynamicDataValueMapper.updateRelease(map);

        req.setTemplateKeys(Strings.join(templateKeys, ','));
        baseMapper.insert(req);

        dynamicDataService.setVersionCache(req.getEnvCode(),req.getAppCode(),req.getId());

    }


}