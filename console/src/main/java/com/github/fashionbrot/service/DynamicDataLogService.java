package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.DynamicDataLogEntity;
import com.github.fashionbrot.request.DynamicDataLogRequest;

/**
 * 配置数据记录表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
public interface DynamicDataLogService  extends IService<DynamicDataLogEntity> {

    Object pageReq(DynamicDataLogRequest req);

    void rollback(Long id);
}