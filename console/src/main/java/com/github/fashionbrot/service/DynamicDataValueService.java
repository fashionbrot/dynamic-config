package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.DynamicDataValueEntity;
import com.github.fashionbrot.request.DynamicDataValueRequest;

/**
 * 动态配置数据表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
public interface DynamicDataValueService  extends IService<DynamicDataValueEntity> {

    Object pageReq(DynamicDataValueRequest req);

}