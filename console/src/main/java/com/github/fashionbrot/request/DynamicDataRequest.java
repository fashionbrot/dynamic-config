package com.github.fashionbrot.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DynamicDataRequest extends PageRequest {

    private String envCode;

    private String appCode;

    private String templateKey;

}
