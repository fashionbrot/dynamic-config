package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.PropertyEntity;
import com.github.fashionbrot.request.CopyPropertyRequest;
import com.github.fashionbrot.request.PropertyRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.PropertyService;
import com.github.fashionbrot.validated.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 属性表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="m:property")
@Controller
@RequestMapping("m/property")
@Api(tags="属性表")
@RequiredArgsConstructor
public class PropertyController  {




    final PropertyService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "m/property/index";
    }

    @MarsPermission(":add")
    @GetMapping("/add")
    public String add(){
        return "m/property/add";
    }

    @MarsPermission(":edit")
    @GetMapping("/edit")
    public String edit(){
        return "m/property/edit";
    }

    @MarsPermission(":copyProperty")
    @GetMapping("/copyProperty")
    public String copyProperty(){
        return "m/property/copyProperty";
    }


    @MarsPermission(":index")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(PropertyRequest req) {
        return Response.success(service.pageReq(req));
    }



    @ApiOperation("数据列表")
    @GetMapping("/queryList")
    @ResponseBody
    public Response queryList(PropertyRequest req){

        return  Response.success(service.queryList(req));
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
    public Response add(@RequestBody PropertyEntity entity){
        service.save(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody PropertyEntity entity){
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
    @MarsPermission(":copyProperty")
    @ApiOperation("复制属性")
    @PostMapping("/copyProperty")
    @ResponseBody
    @Validated
    public Response copyProperty(CopyPropertyRequest req){
        service.copyProperty(req);
        return Response.success();
    }


}