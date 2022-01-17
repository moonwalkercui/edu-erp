package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.entity.DataPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * com.xiangrui.marine.common.entity.DataPermission
 */
@Repository
public interface DataPermissionMapper extends BaseMapper<DataPermission> {

    List<DataPermission> getByStaffIdAndEntityName(Long staffId, String entityName);

}




