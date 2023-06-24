package com.github.fashionbrot.response;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataDynamicResponse {

    private List<DataDynamicJsonResponse> jsonList;

    private List<JSONObject> json;

    private String templateKey;

}
