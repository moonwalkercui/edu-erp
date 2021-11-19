package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.ContactRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.ContactRecordParamDTO;
import com.hzb.erp.common.pojo.vo.ContactRecordVO;

/**
 * <p>
 * 学员跟进表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface ContactRecordMapper extends BaseMapper<ContactRecord> {
    @DataScoped
    IPage<ContactRecordVO> getList(Page<?> objectPage, ContactRecordParamDTO param);
}
