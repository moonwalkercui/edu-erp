package com.hzb.erp.common.pojo.vo;

import com.hzb.erp.common.entity.User;
import lombok.Data;

/**
 * <p>
 * 学员端账号
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class UserVO extends User {
    private Integer studentCount;
    private String studentNames;
}
