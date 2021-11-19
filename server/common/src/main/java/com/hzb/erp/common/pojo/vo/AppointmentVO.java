package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.VerifyStateEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentVO {
    private Long id;
    private Long studentId;
    private String studentName;
    private String lessonTitle;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate addTime;
    private VerifyStateEnum verifyState;
}
