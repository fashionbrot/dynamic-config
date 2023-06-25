package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.TemplateEntity;
import com.github.fashionbrot.request.TemplateRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 模板表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="m:template")
@Controller
@RequestMapping("m/template")
@Api(tags="模板表")
@RequiredArgsConstructor
public class TemplateController  {



    final TemplateService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "m/template/index";
    }

    @MarsPermission(":add")
    @GetMapping("/add")
    public String add(){
        return "m/template/add";
    }

    @MarsPermission(":edit")
    @GetMapping("/edit")
    public String edit(){
        return "m/template/edit";
    }


    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(TemplateRequest req) {
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
    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public Response add(@RequestBody TemplateEntity entity){
        service.add(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody TemplateEntity entity){
        service.edit(entity);
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





}