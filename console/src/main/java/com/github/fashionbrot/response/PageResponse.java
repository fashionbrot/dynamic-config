package com.github.fashionbrot.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("PageResponse")
public class PageResponse<T> {

    @ApiModelProperty("数据")
    private List<T> rows;

    @ApiModelProperty("总页数")
    private long total;

}
