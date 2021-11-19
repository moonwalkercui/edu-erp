package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Attachment对象", description = "附件表")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件地址
     */
    private String url;

    /**
     * 保存路径
     */
    private String savePath;

    /**
     * 文件原名
     */
    private String fileName;

    /**
     * 附件大小
     */
    private Long fileSize;

    /**
     * 附件类型
     */
    private String fileType;

    /**
     * 添加时间
     */
    private LocalDateTime addTime;

    /**
     * 创建时间
     */
    private Long creator;


}
