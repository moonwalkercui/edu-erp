package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.DataPermission;
import com.hzb.erp.common.pojo.DataPermissionSaveDTO;

import java.util.List;

/**
 * 数据权限
 */
public interface DataPermissionService extends IService<DataPermission> {

    List<DataPermission> listByPositionId(Long positionId);

    boolean saveOrUpdateByDTO(DataPermissionSaveDTO dto);

    boolean handleDel(List<Long> ids);

    DataPermission getInfo(Long id);
}
