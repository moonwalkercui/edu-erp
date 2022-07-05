package com.hzb.erp.api.pc.clazz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.clazz.entity.Classroom;
import com.hzb.erp.api.pc.clazz.pojo.ClassroomParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassroomVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 教室 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface ClassroomMapper extends BaseMapper<Classroom> {
    IPage<ClassroomVO> getList(Page<?> objectPage, ClassroomParamDTO param);
}
