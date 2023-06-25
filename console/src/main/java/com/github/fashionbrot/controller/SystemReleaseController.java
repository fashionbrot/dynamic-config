package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SystemReleaseEntity;
import com.github.fashionbrot.request.SystemReleaseRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.SystemReleaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 系统配置发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */

@MarsPermission(value="m:system:release")
@Controller
@RequestMapping("m/system/release")
@Api(tags="系统配置发布表")
@RequiredArgsConstructor
public class SystemReleaseController  {



    final SystemReleaseService service;



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(SystemReleaseRequest req) {
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
    public Response add(@RequestBody SystemReleaseEntity entity){
        service.save(entity);
        return Response.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody SystemReleaseEntity entity){
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