package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysLogEntity;
import com.github.fashionbrot.request.SysLogRequest;

/**
 * 系统日志
 *
 * @author fashionbrot
 */
public interface SysLogService extends IService<SysLogEntity> {

    Object pageReq(SysLogRequest req);

}