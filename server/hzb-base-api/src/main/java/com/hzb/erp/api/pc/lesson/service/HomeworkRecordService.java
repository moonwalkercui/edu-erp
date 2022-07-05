package com.hzb.erp.api.pc.lesson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.HomeworkRecord;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkRecordCommentSaveDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkRecordSaveDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkRecordVO;

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
