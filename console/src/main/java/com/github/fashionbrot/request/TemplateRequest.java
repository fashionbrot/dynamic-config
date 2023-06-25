package com.github.fashionbrot.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TemplateRequest extends PageRequest {

    private String appCode;

}
