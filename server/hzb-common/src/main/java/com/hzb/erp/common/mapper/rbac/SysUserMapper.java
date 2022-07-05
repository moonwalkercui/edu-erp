package com.hzb.erp.common.mapper.rbac;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.rbac.SysUser;
import com.hzb.erp.common.pojo.StaffVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<StaffVO> getUsersByRoleId(Page<Object> objectPage, Long roleId);
}