package com.hzb.erp.common.pojo;

import lombok.Data;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Data
public class UserParamDTO extends PaginateDTO{
    private String keyword;
    // 微信昵称
    private String nickname;
}
