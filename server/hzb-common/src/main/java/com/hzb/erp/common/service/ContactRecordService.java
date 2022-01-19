package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.entity.ContactRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.pojo.dto.ContactRecordParamDTO;
import com.hzb.erp.common.pojo.dto.ContactRecordSaveDTO;
import com.hzb.erp.common.pojo.vo.ContactRecordVO;

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
