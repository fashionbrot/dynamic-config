
package com.github.fashionbrot.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.entity.SysLogEntity;
import com.github.fashionbrot.request.SysLogRequest;
import com.github.fashionbrot.response.SysLogResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 系统日志
 *
 * @author fashionbrot
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {


    List<SysLogResponse> selectListByReq(SysLogRequest req);
}