package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SysLogEntity;
import com.github.fashionbrot.mapper.SysLogMapper;
import com.github.fashionbrot.request.SysLogRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.response.SysLogResponse;
import com.github.fashionbrot.service.SysLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统日志
 *
 * @author fashionbrot
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public Object pageReq(SysLogRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
//        Map<String,Object> map = ConvertUtil.toMap(req);

        List<SysLogResponse> list = sysLogMapper.selectListByReq(req);

        return PageResponse.<SysLogResponse>builder()
                .rows(list)
                .total(page.getTotal())
                .build();

    }

}