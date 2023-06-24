package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.DynamicDataEntity;
import com.github.fashionbrot.request.DataDynamicApiRequest;
import com.github.fashionbrot.request.DynamicDataRequest;
import com.github.fashionbrot.response.ApiResponse;


/**
 * 动态配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
public interface DynamicDataService  extends IService<DynamicDataEntity> {

    Object pageReq(DynamicDataRequest req);

    void add(DynamicDataEntity entity);

    void edit(DynamicDataEntity entity);

    void deleteById(Long id);

    Object selectById(Long id);

    Object checkVersion(DataDynamicApiRequest req);

    ApiResponse getData(DataDynamicApiRequest req);

    Long clusterSync(DataDynamicApiRequest req);

    void setVersionCache(String envCode,String appCode,Long releaseId);
}