package com.github.fashionbrot.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PropertyRequest extends PageRequest {

    private String appCode;

    private String templateKey;

    private Integer showTable;
}
