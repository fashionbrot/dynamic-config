package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.DynamicDataLogEntity;
import com.github.fashionbrot.request.DynamicDataLogRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.DynamicDataLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 配置数据记录表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */

@MarsPermission(value="m:dynamic:data:log")
@Controller
@RequestMapping("m/dynamic/data/log")
@Api(tags="配置数据记录表")
@RequiredArgsConstructor
public class DynamicDataLogController  {



    final DynamicDataLogService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "m/dynamicDataLog/index";
    }

    @GetMapping("/add")
    public String add(){
        return "m/dynamicDataLog/add";
    }

    @GetMapping("/edit")
    public String edit(){
        return "m/dynamicDataLog/edit";
    }

    @MarsPermission(":index")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(DynamicDataLogRequest req) {
        return Response.success(service.pageReq(req));
    }



    @ApiOperation("数据列表")
    @GetMapping("/queryList")
    @ResponseBody
    public Response queryList(@RequestParam Map<String, Object> params){
        return  Response.success(service.listByMap(params));
    }



    @ApiOperation("根据id查询")
    @PostMapping("/selectById")
    @ResponseBody
    public Response selectById(Long id){
        return Response.success(service.getById(id));
    }


    @MarsLog
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public Response add(@RequestBody DynamicDataLogEntity entity){
        service.save(entity);
        return Response.success();
    }



    @MarsLog
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody DynamicDataLogEntity entity){
        service.updateById(entity);
        return Response.success();
    }



    @MarsLog
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public Response deleteById(Long id){
        service.removeById(id);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":rollback")
    @ApiOperation("回滚")
    @PostMapping("/rollback")
    @ResponseBody
    public Response rollback(Long id){
        service.rollback(id);
        return Response.success();
    }



}