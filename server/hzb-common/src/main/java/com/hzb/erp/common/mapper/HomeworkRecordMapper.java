package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.HomeworkRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.HomeworkParamDTO;
import com.hzb.erp.common.pojo.vo.HomeworkRecordVO;

/**
 * <p>
 * 作业提交记录 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface HomeworkRecordMapper extends BaseMapper<HomeworkRecord> {

    IPage<HomeworkRecordVO> getList(Page<Object> page, HomeworkParamDTO param);
}
