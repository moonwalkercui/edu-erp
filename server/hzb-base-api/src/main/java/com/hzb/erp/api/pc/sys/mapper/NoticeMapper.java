package com.hzb.erp.api.pc.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.sys.entity.Notice;
import com.hzb.erp.common.pojo.CommonParamDTO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Entity com.xiangrui.marine.common.entity.Notice
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    IPage<Notice> getList(Page<?> page, CommonParamDTO param);
}




