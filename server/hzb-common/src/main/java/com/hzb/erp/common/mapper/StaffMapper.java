package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.pojo.StaffParamDTO;
import com.hzb.erp.common.pojo.StaffVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 教师员工表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface StaffMapper extends BaseMapper<Staff> {
    @DataScoped
    IPage<StaffVO> getList(Page<Object> page, StaffParamDTO param);

    StaffVO getInfo(Long id);

    List<StaffVO> listByComId(Long comId);

    String getWxOpenid(Long id);
}
