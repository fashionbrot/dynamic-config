package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.EnvEntity;
import com.github.fashionbrot.request.EnvRequest;

/**
 * 环境表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface EnvService  extends IService<EnvEntity> {

    Object pageReq(EnvRequest req);

}