package com.github.fashionbrot.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SystemConfigHistoryRequest extends PageRequest {

    private String envCode;
    private String appCode;

}
