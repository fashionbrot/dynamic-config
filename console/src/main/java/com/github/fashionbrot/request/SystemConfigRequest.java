package com.github.fashionbrot.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfigRequest extends PageRequest {

    private String envCode;

    private String appCode;

    private Integer updateReleaseStatus;

    private List<Integer> whereReleaseStatus;

}
