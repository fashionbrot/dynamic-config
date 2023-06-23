package com.github.fashionbrot.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分页req")
public class PageRequest {

    @ApiModelProperty(value = "当前页码")
    private Integer pageNum=1;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize=10;
}
