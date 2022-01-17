package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Attachment;
import com.hzb.erp.common.service.AttachmentService;
import com.hzb.erp.common.mapper.AttachmentMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    @Override
    public Attachment getByUrl(String url) {
        QueryWrapper<Attachment> qw = new QueryWrapper<>();
        qw.eq("url", url).last("limit 1");
        return getOne(qw);
    }
}




