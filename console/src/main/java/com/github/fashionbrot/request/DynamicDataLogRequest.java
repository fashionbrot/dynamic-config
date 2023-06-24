package com.github.fashionbrot.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class DynamicDataLogRequest extends PageRequest {

    private String envCode;

    private String appCode;

    private String templateKey;

}
