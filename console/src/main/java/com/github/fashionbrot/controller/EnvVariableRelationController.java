package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.EnvVariableRelationEntity;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.EnvVariableRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 常量和环境关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-20
 */

@MarsPermission(value="m:env:variable:relation")
@Controller
@RequestMapping("m/env/variable/relation")
@Api(tags="常量和环境关系表")
@RequiredArgsConstructor
public class EnvVariableRelationController {


    final EnvVariableRelationService service;


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
    public Response add(@RequestBody EnvVariableRelationEntity entity){
        service.save(entity);
        return Response.success();
    }


    @MarsLog
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody EnvVariableRelationEntity entity){
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




}