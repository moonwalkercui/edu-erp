package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Classroom;
import com.hzb.erp.common.pojo.dto.ClassroomParamDTO;
import com.hzb.erp.common.pojo.vo.ClassroomVO;

/**
 * <p>
 * 教室 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface ClassroomMapper extends BaseMapper<Classroom> {
    IPage<ClassroomVO> getList(Page<?> objectPage, ClassroomParamDTO param);
}
