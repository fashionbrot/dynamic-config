package com.github.fashionbrot.request;

import lombok.Data;

@Data
public class DataDynamicApiRequest {


    private String appCode;

    private String envCode;

    private Long version;

    private String templateKeys;

    private String all;

}
