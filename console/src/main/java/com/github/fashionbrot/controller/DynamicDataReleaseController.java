package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.DynamicDataReleaseEntity;
import com.github.fashionbrot.request.DynamicDataReleaseRequest;
import com.github.fashionbrot.service.DynamicDataReleaseService;
import com.github.fashionbrot.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 配置数据发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */

@MarsPermission(value="m:dynamic:data:release")
@Controller
@RequestMapping("m/dynamic/data/release")
@Api(tags="配置数据发布表")
@RequiredArgsConstructor
public class DynamicDataReleaseController  {



    final DynamicDataReleaseService service;



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(DynamicDataReleaseRequest req) {
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

    @MarsLog
    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public Response add(@RequestBody DynamicDataReleaseEntity entity){
        service.save(entity);
        return Response.success();
    }

    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody DynamicDataReleaseEntity entity){
        service.updateById(entity);
        return Response.success();
    }

    @MarsLog
    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public Response deleteById(Long id){
        service.removeById(id);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":deleteByIds")
    @ApiOperation("批量删除")
    @PostMapping("/deleteByIds")
    @ResponseBody
    public Response delete(@RequestBody Long[] ids){
        service.removeByIds(Arrays.asList(ids));
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":release")
    @ApiOperation("发布")
    @PostMapping("/release")
    @ResponseBody
    public Response release( DynamicDataReleaseEntity releaseEntity){
        service.release(releaseEntity);
        return Response.success();
    }




}