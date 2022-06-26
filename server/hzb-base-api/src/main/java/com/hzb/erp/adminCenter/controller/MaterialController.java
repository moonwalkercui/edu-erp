package com.hzb.erp.adminCenter.controller;

import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.base.annotation.PreventMultiSubmit;
import com.hzb.erp.common.entity.Material;
import com.hzb.erp.common.pojo.dto.MaterialParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.MaterialService;
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

    @ApiOperation("物料列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "name", defaultValue = "") String name) {
        MaterialParamDTO param = new MaterialParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setName(name);
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
}
