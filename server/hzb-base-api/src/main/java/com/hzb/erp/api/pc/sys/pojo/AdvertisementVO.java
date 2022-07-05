package com.hzb.erp.api.pc.sys.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.AdvertisementTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 公告
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "AdvertisementVO对象", description = "公告")
public class AdvertisementVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "发送标题")
    private String title;

    @ApiModelProperty(value = "发送内容")
    private String content;

    @ApiModelProperty(value = "图片")
    private String cover;

    @ApiModelProperty(value = "类型")
    private AdvertisementTypeEnum type;

    @ApiModelProperty(value = "创建者")
    private Long creator;

    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "状态")
    private Boolean state;
}
