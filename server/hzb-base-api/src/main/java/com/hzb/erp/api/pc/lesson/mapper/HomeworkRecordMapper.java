package com.hzb.erp.api.pc.lesson.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.lesson.entity.HomeworkRecord;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkRecordVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 作业提交记录 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface HomeworkRecordMapper extends BaseMapper<HomeworkRecord> {

    IPage<HomeworkRecordVO> getList(Page<Object> page, HomeworkParamDTO param);
}
