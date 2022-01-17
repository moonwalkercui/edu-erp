package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Notice;
import com.hzb.erp.common.pojo.dto.CommonParamDTO;


/**
 * @Entity com.xiangrui.marine.common.entity.Notice
 */
public interface NoticeMapper extends BaseMapper<Notice> {
    IPage<Notice> getList(Page<?> page, CommonParamDTO param);
}




