package com.hzb.erp.api.pc.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hzb.erp.api.pc.sys.entity.Notice;
import com.hzb.erp.api.pc.sys.service.NoticeService;
import com.hzb.erp.api.pc.sys.mapper.NoticeMapper;
import com.hzb.erp.common.pojo.CommonParamDTO;
import com.hzb.erp.api.pc.sys.pojo.NoticeValidDTO;
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




