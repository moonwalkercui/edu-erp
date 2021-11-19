package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class IdsAndContentDTO {
    private List<Long> ids;
    private String content;
}
