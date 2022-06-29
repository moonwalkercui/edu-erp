package com.hzb.erp.studentMobile.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author Ryan 541720500@qq.com
 * 用户信息
 */
@Data
public class UserVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @NotBlank(message = "缺少姓名")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime addTime;

}
