package com.github.fashionbrot.request;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SystemConfigRequest extends PageRequest {

    private String envCode;

    private String appCode;

    private Integer updateReleaseStatus;

    private List<Integer> whereReleaseStatus;

}
