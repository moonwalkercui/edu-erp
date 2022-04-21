package com.hzb.erp.common.mapper.rbac;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.constants.CacheNames;
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

    @Cacheable(value = CacheNames.SYS_PERMISSION_LIST)
    List<SysPermissionDTO> getAllBaseInfo();

    List<SysPermission> getByRoleId(Long roleId);

    List<SysPermission> getByRoleIds(List<Long> roleIds);
}