package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseParamDTO extends PaginateDTO {
    private Long subjectId;
    private Long linkId;
    private Integer lessonType;
    //    private Integer state;
    private List<Integer> state;
    private String name;
    private Boolean forSale;
    private Boolean recommend;
    private Boolean withFavRate;
}
