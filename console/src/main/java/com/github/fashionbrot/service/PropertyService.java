package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.PropertyEntity;
import com.github.fashionbrot.request.CopyPropertyRequest;
import com.github.fashionbrot.request.PropertyRequest;

/**
 * 属性表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface PropertyService  extends IService<PropertyEntity> {

    Object pageReq(PropertyRequest req);

    Object queryList(PropertyRequest params);

    void copyProperty(CopyPropertyRequest req);
}