package com.github.fashionbrot.request;

import lombok.Data;

@Data
public class DynamicDataRequest extends PageRequest {

    private String envCode;

    private String appCode;

    private String templateKey;

}
