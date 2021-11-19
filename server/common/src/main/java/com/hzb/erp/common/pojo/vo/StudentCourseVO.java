package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.FinanceStateEnum;
import com.hzb.erp.common.enums.VerifyStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 学生表vo
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class StudentCourseVO implements Serializable {
    private Long id;

    private Long studentId;

    private String studentName;

    private Long courseId;

    private String courseName;

    private String subjectName;

    private Integer countLessonTotal;

    private Integer countLessonComplete;

    private Integer countLessonRemaining;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "课程金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "实付金额")
    private BigDecimal paidAmount;

    @ApiModelProperty(value = "折扣金额")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "欠费")
    private BigDecimal arrearage;

    @ApiModelProperty(value = "剩余课次金额")
    private BigDecimal remainingAmount;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "有效期至")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "退费总金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "退费总课次")
    private BigDecimal refundLessonCount;

    @ApiModelProperty(value = "退费总课次")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "退费说明")
    private String refundRemark;

    @ApiModelProperty(value = "退费状态")
    private VerifyStateEnum refundState;

    @ApiModelProperty(value = "财务审核状态")
    private FinanceStateEnum verifyState;

    @ApiModelProperty(value = "消课优先级")
    private Integer priority;

    private Boolean expired;
    public Boolean getExpired() {
        return expireDate == null ? null : LocalDate.now().isAfter(expireDate);
    }

    private Boolean warning;
    public Boolean getWarning() {
        return countLessonRemaining == null ? null : countLessonRemaining <= 5;
    }
}
