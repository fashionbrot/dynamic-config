package com.github.fashionbrot.request;

import lombok.Data;

@Data
public class PropertyRequest extends PageRequest {

    private String appCode;

    private String templateKey;

    private Integer showTable;
}
