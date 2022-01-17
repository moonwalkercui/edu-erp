package com.hzb.erp.common.mapper.rbac;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.entity.rbac.SysPermission;
import com.hzb.erp.common.entity.rbac.SysPermissionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<SysPermissionDTO> getAllInfo();

    @Cacheable(cacheNames = "SysPermissionListV3")
    List<SysPermissionDTO> getAllBaseInfo();

    List<SysPermission> getByRoleId(Long roleId);

    List<SysPermission> getByRoleIds(List<Long> roleIds);
}