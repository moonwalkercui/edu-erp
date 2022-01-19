package com.hzb.erp.common.service;

import com.hzb.erp.common.entity.Attachment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface AttachmentService extends IService<Attachment> {

    Attachment getByUrl(String url);
}
