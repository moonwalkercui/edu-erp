package com.hzb.erp.wechat.service.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxAccessParamDTO extends PaginateDTO {
    private String keyword;
    // 微信昵称
    private String nickname;
}
