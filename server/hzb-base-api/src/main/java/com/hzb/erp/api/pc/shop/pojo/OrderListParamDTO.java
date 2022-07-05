package com.hzb.erp.api.pc.shop.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class OrderListParamDTO extends PaginateDTO {
    private List<Integer> state;
    private String sn;
    private Long studentId;
    private Long courseId;
    private LocalDate endDate;
    private LocalDate startDate;
}
