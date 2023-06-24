package com.github.fashionbrot.request;

import lombok.Data;

@Data
public class SystemConfigHistoryRequest extends PageRequest {

    private String envCode;
    private String appCode;

}
