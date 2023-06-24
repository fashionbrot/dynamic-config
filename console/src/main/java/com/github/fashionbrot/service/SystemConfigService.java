package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SystemConfigEntity;
import com.github.fashionbrot.request.DataConfigRequest;
import com.github.fashionbrot.request.SystemConfigApiRequest;
import com.github.fashionbrot.request.SystemConfigRequest;
import com.github.fashionbrot.response.ForDataListResponse;


/**
 * 应用系统配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-12
 */
public interface SystemConfigService extends IService<SystemConfigEntity> {

    Object pageReq(SystemConfigRequest req);

    void add(SystemConfigEntity entity);

    void edit(SystemConfigEntity entity);

    void deleteById(Long id);

    Object selectById(Long id);

    void undel(Long id);

    void releaseConfig(SystemConfigEntity req);

    long checkForUpdate(DataConfigRequest dataConfig);

    ForDataListResponse forDataVo(DataConfigRequest dataConfig);

    Long clusterSync(SystemConfigApiRequest apiReq);

   void updateRelease(String envCode, String appCode, String fileName);
}