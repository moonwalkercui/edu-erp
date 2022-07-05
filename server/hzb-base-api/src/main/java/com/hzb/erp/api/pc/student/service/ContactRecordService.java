package com.hzb.erp.api.pc.student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.api.pc.student.entity.ContactRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.student.pojo.ContactRecordParamDTO;
import com.hzb.erp.api.pc.student.pojo.ContactRecordSaveDTO;
import com.hzb.erp.api.pc.student.pojo.ContactRecordVO;

/**
 * <p>
 * 学员跟进表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface ContactRecordService extends IService<ContactRecord> {

    IPage<ContactRecordVO> getList(ContactRecordParamDTO param);

    Boolean saveOrUpdateByDTO(ContactRecordSaveDTO contactRecordSaveDTO);
}
