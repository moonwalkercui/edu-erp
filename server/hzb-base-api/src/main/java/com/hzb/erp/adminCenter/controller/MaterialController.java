package com.hzb.erp.adminCenter.controller;

import com.aliyuncs.utils.AuthUtils;
import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.base.annotation.PreventMultiSubmit;
import com.hzb.erp.common.entity.CreditMall;
import com.hzb.erp.common.entity.Material;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.common.pojo.dto.MaterialParamDTO;
import com.hzb.erp.common.pojo.dto.MaterialRecordParamDTO;
import com.hzb.erp.common.pojo.dto.MaterialStorageDTO;
import com.hzb.erp.common.pojo.dto.StudentCreditLogParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.MaterialRecordService;
import com.hzb.erp.common.service.MaterialService;
import com.hzb.erp.security.Util.JwtUserDetails;
import com.hzb.erp.security.Util.UserAuthUtil;
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
 * 物料 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/material")
@Api(value = "物料管理", tags = "物料管理")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialRecordService materialRecordService;

    @ApiOperation("物料列表")
    @GetMapping("/list")
    public PaginationVO list(MaterialParamDTO param) {
        return JsonResponseUtil.paginate(materialService.getList(param));
    }

    @ApiOperation("创建和修改物料")
    @Log(description = "创建和修改物料", type = "物料管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody Material dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (materialService.saveOrUpdateByDTO(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("删除物料")
    @Log(description = "删除物料", type = "物料管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (materialService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("启用物料")
    @Log(description = "启用物料", type = "物料管理")
    @PostMapping("/open")
    public JsonResponse open(@RequestBody List<Long> ids) {
        if (materialService.switchState(ids, SwitchEnum.YES)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("禁用物料")
    @Log(description = "禁用物料", type = "物料管理")
    @PostMapping("/close")
    public JsonResponse close(@RequestBody List<Long> ids) {
        if (materialService.switchState(ids, SwitchEnum.NO)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("物料入库")
    @Log(description = "物料入库", type = "物料管理")
    @PostMapping("/handleIn")
    public JsonResponse handleIn(@Valid @RequestBody MaterialStorageDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        dto.setInStorage(true);
        if (materialService.handleStorage(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("物料出库")
    @Log(description = "物料出库", type = "物料管理")
    @PostMapping("/handleOut")
    public JsonResponse handleOut(@Valid @RequestBody MaterialStorageDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        dto.setInStorage(false);
        if (materialService.handleStorage(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("出入库记录")
    @GetMapping("/storageRecord")
    public PaginationVO creditLog(MaterialRecordParamDTO param) {
        return JsonResponseUtil.paginate(materialRecordService.getList(param));
    }

}
