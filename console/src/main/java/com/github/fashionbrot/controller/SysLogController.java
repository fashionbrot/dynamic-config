package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysLogEntity;
import com.github.fashionbrot.entity.SysUserEntity;
import com.github.fashionbrot.request.SysLogRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.SysLogService;
import com.github.fashionbrot.service.SysUserService;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 系统日志
 *
 * @author fashionbrot
 */

@MarsPermission(value="sys:log")
@Controller
@RequestMapping("sys/log")
@Api(tags="系统日志")
@ApiSort(23735114)
@RequiredArgsConstructor
public class SysLogController {




    final SysLogService service;
    final SysUserService sysUserService;


    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "system/log/log";
    }


    @ApiOperation("查看日志详情")
    @MarsPermission(":index:detail")
    @GetMapping("/index/detail")
    public String detail( Long id, ModelMap modelMap){
        SysLogEntity data = service.getById(id);
        if (data!=null){
            SysUserEntity byId = sysUserService.getById(data.getCreateId());
            if (byId!=null){
                data.setCreateName(byId.getUserName());
            }
        }
        modelMap.put("operLog",data);
        return "system/log/detail";
    }





    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(SysLogRequest req) {
        return Response.success(service.pageReq(req));
    }


    @MarsPermission(":queryList")
    @ApiOperation("数据列表")
    @GetMapping("/queryList")
    @ResponseBody
    public Response queryList(@RequestParam Map<String, Object> params){
        return  Response.success(service.listByMap(params));
    }


    @MarsPermission(":selectById")
    @ApiOperation("根据id查询")
    @PostMapping("/selectById")
    @ResponseBody
    public Response selectById(Long id){
        return Response.success(service.getById(id));
    }

    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public Response add(@RequestBody SysLogEntity entity){
        service.save(entity);
        return Response.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody SysLogEntity entity){
        service.updateById(entity);
        return Response.success();
    }


    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public Response deleteById(Long id){
        service.removeById(id);
        return Response.success();
    }


    @MarsPermission(":deleteByIds")
    @ApiOperation("批量删除")
    @PostMapping("/deleteByIds")
    @ResponseBody
    public Response delete(@RequestBody Long[] ids){
        service.removeByIds(Arrays.asList(ids));
        return Response.success();
    }



}