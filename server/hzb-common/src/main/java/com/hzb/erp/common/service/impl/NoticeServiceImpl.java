package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hzb.erp.common.entity.Notice;
import com.hzb.erp.common.mapper.NoticeMapper;
import com.hzb.erp.common.pojo.dto.CommonParamDTO;
import com.hzb.erp.common.pojo.dto.NoticeValidDTO;
import com.hzb.erp.common.service.NoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public boolean saveOrUpdateByDTO(NoticeValidDTO dto) {
        Notice item = new Notice();
        BeanUtils.copyProperties(dto, item);
        return this.saveOrUpdate(item);
    }

    @Override
    public IPage<Notice> getList(CommonParamDTO param) {
        return baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }
}




