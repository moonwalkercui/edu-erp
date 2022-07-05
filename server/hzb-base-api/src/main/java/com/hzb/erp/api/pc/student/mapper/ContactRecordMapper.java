package com.hzb.erp.api.pc.student.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.student.entity.ContactRecord;
import com.hzb.erp.api.pc.student.pojo.ContactRecordParamDTO;
import com.hzb.erp.api.pc.student.pojo.ContactRecordVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学员跟进表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface ContactRecordMapper extends BaseMapper<ContactRecord> {
    @DataScoped
    IPage<ContactRecordVO> getList(Page<?> objectPage, ContactRecordParamDTO param);
}
