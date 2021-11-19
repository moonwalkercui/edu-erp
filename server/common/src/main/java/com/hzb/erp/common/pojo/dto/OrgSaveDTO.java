package com.hzb.erp.common.pojo.dto;

import com.hzb.erp.common.entity.Org;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
* 通用参数接收类
* */

@Data
public class OrgSaveDTO extends Org {

    @NotBlank(message = "缺少名称")
    private String name;

    private List<Integer> region;
    private List<String> routeList;

}
