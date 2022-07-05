package com.hzb.erp.api.pc.student.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.student.entity.ContactRecord;
import com.hzb.erp.api.pc.student.mapper.ContactRecordMapper;
import com.hzb.erp.api.pc.student.pojo.ContactRecordParamDTO;
import com.hzb.erp.api.pc.student.pojo.ContactRecordSaveDTO;
import com.hzb.erp.api.pc.student.pojo.ContactRecordVO;
import com.hzb.erp.api.pc.student.service.ContactRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学员跟进表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class ContactRecordServiceImpl extends ServiceImpl<ContactRecordMapper, ContactRecord> implements ContactRecordService {

    @Override
    public IPage<ContactRecordVO> getList(ContactRecordParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(ContactRecordSaveDTO contactRecordSaveDTO) {
        ContactRecord student = new ContactRecord();
        BeanUtils.copyProperties(contactRecordSaveDTO, student);
        return this.saveOrUpdate(student);
    }
}
