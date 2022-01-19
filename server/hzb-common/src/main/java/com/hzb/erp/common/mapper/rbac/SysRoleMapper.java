package com.hzb.erp.common.mapper.rbac;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.entity.rbac.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> selectRoleByUser(Long userId);

    List<SysRole> selectRoleByPermission(Long permissionId);
}