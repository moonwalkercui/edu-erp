package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.pojo.dto.CommonParamDTO;
import com.hzb.erp.common.pojo.dto.NameValidDTO;
import com.hzb.erp.common.pojo.dto.PositionSetDTO;
import com.hzb.erp.common.service.PositionService;
import com.hzb.erp.common.service.StaffOrginfoService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 职位控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/position")
@Api(value = "职位管理", tags = "职位管理")
public class PositionController {
    @Autowired
    private PositionService positionService;
    @Autowired
    private StaffOrginfoService staffOrginfoService;
    @ApiOperation("职位列表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "name", defaultValue = "") String name) {
        CommonParamDTO param = new CommonParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setName(name);
        return page == null ? positionService.getAll(param)
                : JsonResponseUtil.paginate(positionService.getList(param));
    }

    @ApiOperation("创建和修改职位")
    @Log(description = "创建和修改职位", type = "职位管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody NameValidDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (positionService.saveOrUpdateByDTO(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除职位")
    @Log(description = "删除职位", type = "职位管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (positionService.handleDel(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("设置员工职位")
    @Log(description = "设置员工职位", type = "职位管理")
    @PostMapping("/setStaffPosition")
    @PreventMultiSubmit
    public JsonResponse setStaffPosition(@Valid @RequestBody PositionSetDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        dto.setCreator(UserAuthService.getCurrentUserId());
        if (staffOrginfoService.setStaffPosition(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

}
