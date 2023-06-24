package com.github.fashionbrot.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataConfigRequest {

    private String envCode;

    private String appCode;

    private Long version;

    private Boolean first;

    private String token;
}
