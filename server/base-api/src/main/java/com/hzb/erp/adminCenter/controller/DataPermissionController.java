package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.entity.DataPermission;
import com.hzb.erp.common.pojo.dto.DataPermissionSaveDTO;
import com.hzb.erp.common.service.DataPermissionService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 数据权限控制
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/dataPermission")
@Api(value = "数据权限控制", tags = "数据权限控制")
public class DataPermissionController {

    @Autowired
    private DataPermissionService dataPermissionService;

    @ApiOperation("权限权限配置信息")
    @GetMapping("/info")
    public DataPermission info(@RequestParam("id") Long id) {
        return dataPermissionService.getInfo(id);
    }

    @ApiOperation("权限列表")
    @GetMapping("/list")
    public List<DataPermission> list(@RequestParam(value = "positionId") Long positionId) {
        return dataPermissionService.listByPositionId(positionId);
    }

    @ApiOperation("创建和修改数据权限")
    @Log(description = "创建和修改数据权限", type = "数据权限管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody DataPermissionSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (dataPermissionService.saveOrUpdateByDTO(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除数据权限")
    @Log(description = "删除数据权限", type = "数据权限管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (dataPermissionService.handleDel(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

}
