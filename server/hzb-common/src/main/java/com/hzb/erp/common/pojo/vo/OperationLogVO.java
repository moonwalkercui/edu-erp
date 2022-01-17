package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "OperationLog对象", description = "操作日志")
public class OperationLogVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    private Long operator;

    private String operatorName;

    private Long studentId;

    private String studentName;

    private String info;

    private String path;

    private String type;

    private String method;

    private String url;

    private String param;

    private String ip;

    private String browserName;

    private String browserVer;

    private String osName;

    private Long timeCost;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

}
