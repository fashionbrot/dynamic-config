package com.github.fashionbrot.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private int code;
    private String msg;
    private Object data;
    private Long version;
}
