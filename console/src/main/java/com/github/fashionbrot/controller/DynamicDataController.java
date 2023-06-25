package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.DynamicDataEntity;
import com.github.fashionbrot.request.DynamicDataRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.DynamicDataService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 动态配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */

@MarsPermission(value="m:dynamic:data")
@Controller
@RequestMapping("m/dynamic/data")
@Api(tags="动态配置表")
@ApiSort(23816652)
@RequiredArgsConstructor
public class DynamicDataController  {




    final DynamicDataService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "m/dynamicData/index";
    }

    @MarsPermission(":add")
    @GetMapping("/add")
    public String add(){
        return "m/dynamicData/add";
    }

    @MarsPermission(":edit")
    @GetMapping("/edit")
    public String edit(){
        return "m/dynamicData/edit";
    }



    @MarsPermission(":index")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(DynamicDataRequest req) {
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
        return Response.success(service.selectById(id));
    }


    @MarsLog
    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public Response add(@RequestBody DynamicDataEntity entity){
        service.add(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody DynamicDataEntity entity){
        service.edit(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public Response deleteById(Long id){
        service.deleteById(id);
        return Response.success();
    }





}