package com.github.fashionbrot.request;

import lombok.Data;

@Data
public class SystemConfigApiRequest {

    private String envCode;

    private String appCode;

    private Long version;

    private String templateKeys;

    private String all;
}
