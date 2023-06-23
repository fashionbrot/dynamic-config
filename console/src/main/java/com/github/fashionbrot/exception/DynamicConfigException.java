package com.github.fashionbrot.exception;


import com.github.fashionbrot.enums.RespEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DynamicConfigException extends RuntimeException {

    private int code;
    private String msg;


    public DynamicConfigException(String msg){
        super(msg);
        this.code = RespEnum.FAIL.getCode();
        this.msg = msg;
    }

    public DynamicConfigException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public DynamicConfigException(RespEnum respCode){
        super(respCode.getMsg());
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    public DynamicConfigException(RespEnum respCode, String msg){
        super(respCode.getMsg());
        this.code = respCode.getCode();
        this.msg = msg+respCode.getMsg();
    }


    public static void throwMsg(String msg){
        throw new DynamicConfigException(msg);
    }
    public static void throwMsg(int code,String msg){
        throw new DynamicConfigException(code,msg);
    }

    public static void throwMsg(RespEnum respEnum){
        throw new DynamicConfigException(respEnum);
    }

}
