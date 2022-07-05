package com.hzb.erp.common.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class IdsAndContentDTO {

    @NotNull
    private List<Long> ids;
    private String content;
}
