package com.github.fashionbrot.response;


import com.github.fashionbrot.consts.GlobalConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* 统一返回vo 类
 * @author fashi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Response统一返回类型")
public class Response<T> implements Serializable{
    private static final long serialVersionUID = -3655390020082644681L;

    public static final int SUCCESS = 0;
    public static final int FAILED = 1;


    @ApiModelProperty("0成功,其他失败")
    private int code;
    @ApiModelProperty("成功失败消息")
    private String msg;
    @ApiModelProperty("返回的数据")
    private T data;


    public static Response fail(String msg){
        return Response.builder().code(FAILED).msg(msg).build();
    }

    public static Response fail(String msg, int code){
        return Response.builder().code(code).msg(msg).build();
    }

    public static<T> Response success(T data){
        return Response.builder().code(SUCCESS).msg("成功").data(data).build();
    }

    public static Response success(){
        return GlobalConst.vo;
    }

}
