package com.hzb.erp.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class ContactRecordSaveDTO {

    private Long id;

    @NotNull(message = "缺少参数studentId")
    private Long studentId;

    private String contactPhone;

    @NotNull(message = "缺少联系时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime contactTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime contactNextTime;

    private ContactTypeEnum contactType;

    @NotBlank(message = "缺少跟进记录")
    private String info;

    @NotNull(message = "缺少跟进阶段")
    private ContactStageEnum stage;
}
