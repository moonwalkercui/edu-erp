package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.DataPermission;
import com.hzb.erp.common.entity.DataPermissionCustom;
import com.hzb.erp.common.enums.DataScopeTypeEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.DataPermissionCustomMapper;
import com.hzb.erp.common.mapper.DataPermissionMapper;
import com.hzb.erp.common.pojo.dto.DataPermissionSaveDTO;
import com.hzb.erp.common.service.DataPermissionService;
import com.hzb.erp.dataScope.DataScopeEntityEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据权限
 */
@Service
public class DataPermissionServiceImpl extends ServiceImpl<DataPermissionMapper, DataPermission> implements DataPermissionService {

    @Resource
    private DataPermissionCustomMapper dataPermissionCustomMapper;

    @Override
    public List<DataPermission> listByPositionId(Long positionId) {

        QueryWrapper<DataPermission> qw = new QueryWrapper<>();
        qw.eq("position_id", positionId);
        List<DataPermission> list = list(qw);
        for (DataPermission dp : list) {
            QueryWrapper<DataPermissionCustom> qw1 = new QueryWrapper<>();
            qw1.eq("permission_id", dp.getId());
            dp.setCustomList(dataPermissionCustomMapper.selectList(qw1));
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateByDTO(DataPermissionSaveDTO dto) {
        if (DataScopeTypeEnum.CUSTOM.equals(dto.getScopeType())) {
            if (dto.getOrgIds().size() == 0) {
                throw new BizException("自定义模式下未选择机构");
            }
        }

        DataPermission item = new DataPermission();
        BeanUtils.copyProperties(dto, item);

        if(StringUtils.isBlank(item.getOwnerField())) {
            for(DataScopeEntityEnum entityEnum: DataScopeEntityEnum.values()) {
                if(entityEnum.getCode().equals(item.getEntityName())) {
                    item.setOwnerField(entityEnum.getDefaultField());
                    break;
                }
            }
        }

        boolean res = saveOrUpdate(item);

        if (DataScopeTypeEnum.CUSTOM.equals(dto.getScopeType())) {
            QueryWrapper<DataPermissionCustom> qw = new QueryWrapper<>();
            qw.eq("permission_id", item.getId());
            dataPermissionCustomMapper.delete(qw);
            for (Long orgId : dto.getOrgIds()) {
                DataPermissionCustom dpc = new DataPermissionCustom();
                dpc.setPermissionId(item.getId());
                dpc.setOrgId(orgId);
                dataPermissionCustomMapper.insert(dpc);
            }
        }

        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleDel(List<Long> ids) {
        for (Long pid : ids) {
            QueryWrapper<DataPermissionCustom> qw = new QueryWrapper<>();
            qw.eq("permission_id", pid);
            dataPermissionCustomMapper.delete(qw);
        }
        return removeByIds(ids);
    }

    @Override
    public DataPermission getInfo(Long id) {
        DataPermission item = getById(id);
        QueryWrapper<DataPermissionCustom> qw = new QueryWrapper<>();
        qw.eq("permission_id", id);
        item.setCustomList(dataPermissionCustomMapper.selectList(qw));
        return item;
    }
}




