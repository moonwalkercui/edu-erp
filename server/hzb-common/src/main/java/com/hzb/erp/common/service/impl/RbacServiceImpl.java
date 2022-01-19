package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.rbac.*;
import com.hzb.erp.common.mapper.rbac.*;
import com.hzb.erp.common.pojo.dto.BatchBindRoleToUserDTO;
import com.hzb.erp.common.pojo.dto.PaginateDTO;
import com.hzb.erp.common.pojo.vo.StaffVO;
import com.hzb.erp.common.service.RbacService;
import com.hzb.erp.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class RbacServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements RbacService {

    @Resource
    private SysPermissionMapper permissionMapper;

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysRolePermissionMapper rolePermissionMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;


    @Override
    public IPage<SysRole> getRoleList(PaginateDTO paginateDTO) {
        return roleMapper.selectPage(new Page<>(paginateDTO.getPage(), paginateDTO.getPageSize()), null);
    }

    @Override
    public SysRole getRoleByCode(String code) {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.eq("code", code);
        return roleMapper.selectOne(qw);
    }

    @Override
    public IPage<StaffVO> getUsersByRoleId(PaginateDTO paginateDTO, Long roleId) {
        return sysUserMapper.getUsersByRoleId(new Page<>(paginateDTO.getPage(), paginateDTO.getPageSize()), roleId);
    }

    @Override
    public List<SysPermission> getPermissionsList() {
        return permissionMapper.selectList(null);
    }

    @Override
    public List<SysPermission> getPermissionsByRoleId(Long roleId) {
        return permissionMapper.getByRoleId(roleId);
    }

    @Override
    public List<String> getPermissionCodesByRoleIds(List<Long> roleIds) {

        List<SysPermission> pmList = permissionMapper.getByRoleIds(roleIds);
        List<String> permissionCodes = new ArrayList<>();
        if (pmList != null && pmList.size() > 0) {
            for (SysPermission p : pmList) {
                if (StringUtils.isNotBlank(p.getCode())) {
                    permissionCodes.add(p.getCode());
                }
            }
        }
        return permissionCodes;
    }

    @Override
    public int saveRole(SysRole role) {

        if (role.getId() != null) {
            SysRole find = getRoleByCode(role.getCode());
            if (find != null && !find.getId().equals(role.getId())) {
                throw new BizException("该编码不能重复");
            }
            isSuperAdmin(find);
        }

        if (role.getId() != null) {
            return roleMapper.updateById(role);
        } else {
            return roleMapper.insert(role);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRole(Long id) {
        SysRole find = roleMapper.selectById(id);
        isSuperAdmin(find);

        QueryWrapper<SysUserRole> qw1 = new QueryWrapper<>();
        qw1.eq("role_id", id);
        if (userRoleMapper.selectList(qw1).size() > 0) {
            throw new BizException("有用户绑定了该角色,无法直接删除");
        }

        // 清空角色权限中间表
        QueryWrapper<SysRolePermission> qw2 = new QueryWrapper<>();
        qw2.eq("role_id", id);
        rolePermissionMapper.delete(qw2);

        return roleMapper.deleteById(id);
    }

    @Override
    public int bindRoleToUsers(Long roleId, List<Long> userIds) {
        QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        List<SysUserRole> existList = userRoleMapper.selectList(qw);

        int res = 0;
        loopLabel:
        for (Long uid : userIds) {
            if (existList != null && existList.size() > 0) {
                for (SysUserRole item : existList) {
                    if (item.getUserId().equals(uid) && item.getRoleId().equals(roleId)) {
                        continue loopLabel;
                    }
                }
            }
            SysUserRole item = new SysUserRole();
            item.setRoleId(roleId);
            item.setUserId(uid);
            userRoleMapper.insert(item);
            res++;

        }
        return res;
    }

    @Override
    public boolean batchBindRoleToUsers(BatchBindRoleToUserDTO dto) {
        int res = 0;
        for (Long staffId : dto.getStaffIds()) {
            QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
            qw.eq("user_id", staffId);
            userRoleMapper.delete(qw);
        }
        for (Long roleId : dto.getRoleIds()) {
            res += bindRoleToUsers(roleId, dto.getStaffIds());
        }
        return res > 0;
    }

    @Override
    public int deleteRoleUser(Long roleId, List<Long> userIds) {
        QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        qw.in("user_id", userIds);
        return userRoleMapper.delete(qw);
    }

    @Override
    public int bindPermissionsToRole(Long roleId, List<Long> permissionIds) {
        QueryWrapper<SysRolePermission> qw1 = new QueryWrapper<>();
        qw1.eq("role_id", roleId);
        rolePermissionMapper.delete(qw1);

        int res = 0;
        for (Long pid : permissionIds) {
            SysRolePermission item = new SysRolePermission();
            item.setRoleId(roleId);
            item.setPermissionId(pid);
            rolePermissionMapper.insert(item);
            res++;
        }
        return res;
    }

    private void isSuperAdmin(SysRole find) {
        if (find == null) {
            return;
        }
        if ("superadmin".equals(find.getCode())) {
            throw new BizException("超级管理员不能删除");
        }
    }
}
