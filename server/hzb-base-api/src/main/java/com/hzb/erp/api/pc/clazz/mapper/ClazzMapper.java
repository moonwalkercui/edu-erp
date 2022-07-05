package com.hzb.erp.api.pc.clazz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.api.pc.clazz.entity.Clazz;
import com.hzb.erp.api.pc.clazz.pojo.ClassParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 班级 Mapper 接口
 * </p>
 *
 * @author 541720500@qq.com
 */
@Mapper
public interface ClazzMapper extends BaseMapper<Clazz> {
    @DataScoped
    IPage<ClassVO> getList(Page<?> page, ClassParamDTO param);

    @DataScoped
    List<ClassVO> getList(@Param("param") ClassParamDTO param);

    ClassVO getInfo(Long id);
}
