package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.rbac.SysPermission;
import com.hzb.erp.common.entity.rbac.SysRole;
import com.hzb.erp.common.pojo.dto.BatchBindRoleToUserDTO;
import com.hzb.erp.common.pojo.dto.PaginateDTO;
import com.hzb.erp.common.pojo.vo.StaffVO;

import java.util.List;

/**
 * <p>
 * 权限控制 服务类
 * </p>
 *
 * @author Ryan
 */
public interface RbacService extends IService<SysRole> {

    /**
     * 获取角色列表
     */
    IPage<SysRole> getRoleList(PaginateDTO paginateDTO);

    /**
     * 通过code获取角色
     */
    SysRole getRoleByCode(String code);

    /**
     * 获取角色的用户列表
     */
    IPage<StaffVO> getUsersByRoleId(PaginateDTO paginateDTO, Long roleId);

    /**
     * 获取权限列表
     */
    List<SysPermission> getPermissionsList();

    /**
     * 通过角色id获取权限列表
     */
    List<SysPermission> getPermissionsByRoleId(Long roleId);

    List<String> getPermissionCodesByRoleIds(List<Long> roleId);

    /**
     * 保存角色 和更新角色
     */
    int saveRole(SysRole role);

    /**
     * 删除角色
     */
    int deleteRole(Long id);

    /**
     * 绑定角色和用户
     */
    int bindRoleToUsers(Long roleId, List<Long> userIds);

    /**
     * 批量绑定角色和用户
     */
    boolean batchBindRoleToUsers(BatchBindRoleToUserDTO dto);

    /**
     * 解绑角色和用户
     */
    int deleteRoleUser(Long roleId, List<Long> userIds);

    /**
     * 解绑角色和权限 含更新
     */
    int bindPermissionsToRole(Long roleId, List<Long> permissionIds);


}
