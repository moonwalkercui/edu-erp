package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.entity.HomeworkRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.pojo.dto.HomeworkParamDTO;
import com.hzb.erp.common.pojo.dto.HomeworkRecordCommentSaveDTO;
import com.hzb.erp.common.pojo.dto.HomeworkRecordSaveDTO;
import com.hzb.erp.common.pojo.vo.HomeworkRecordVO;

/**
 * <p>
 * 作业提交记录 服务类
 * </p>
 *
 * @author Ryan
 */
public interface HomeworkRecordService extends IService<HomeworkRecord> {

    boolean comment(HomeworkRecordCommentSaveDTO dto, Long teacherId);

    IPage<HomeworkRecordVO> getList(HomeworkParamDTO param);

    boolean deleteByStudentId(Long id, Long studentId);

    boolean saveRecord(HomeworkRecordSaveDTO dto);
}
