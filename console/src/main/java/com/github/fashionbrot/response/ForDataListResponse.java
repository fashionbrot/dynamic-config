package com.github.fashionbrot.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForDataListResponse {

    private List<ForDataResponse> list;

    private Long version;
}
