package com.hzb.erp.studentCenter.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author Ryan 541720500@qq.com
 * 用户信息
 */
@Data
public class UserVo {
    private Long id;
    @NotBlank(message = "缺少姓名")
    private String name;
    private String nickname;
    private String mobile;
    private String headImg;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime addTime;
}
