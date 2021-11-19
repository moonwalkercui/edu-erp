package com.hzb.erp.adminCenter;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.pojo.dto.OrgParamDTO;
import com.hzb.erp.common.pojo.dto.OrgPutinStaffDTO;
import com.hzb.erp.common.pojo.dto.OrgSaveDTO;
import com.hzb.erp.common.pojo.vo.OrgVO;
import com.hzb.erp.common.service.OrgService;
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
 * 机构控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/org")
@Api(value = "机构管理", tags = "机构管理")
public class OrgController {
    @Autowired
    private OrgService orgService;
    @Autowired
    private StaffOrginfoService staffOrginfoService;
    @ApiOperation("机构信息")
    @GetMapping("/info")
    public OrgVO info(@RequestParam(value = "id") Long id) {
        return orgService.getInfo(id);
    }

    @ApiOperation("机构列表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "name", defaultValue = "") String name,
                       @RequestParam(value = "pid", defaultValue = "") Long pid,
                       @RequestParam(value = "type", defaultValue = "") Integer type,
                       @RequestParam(value = "level", defaultValue = "") Integer level) {
        OrgParamDTO param = new OrgParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setName(name);
        param.setLevel(level);
        param.setType(type);
        param.setPid(pid);
        return page == null ? orgService.getAll(param)
                : JsonResponseUtil.paginate(orgService.getList(param));
    }

    @ApiOperation("创建和修改机构")
    @Log(description = "创建和修改机构", type = "机构管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody OrgSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (orgService.saveOrUpdateByDTO(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除机构")
    @Log(description = "删除机构", type = "机构管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {

        if (orgService.handleDel(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("转入人员")
    @Log(description = "转入人员", type = "人员管理")
    @PostMapping("/putinStaff")
    public JsonResponse putinStaff(@Valid @RequestBody OrgPutinStaffDTO dto, BindingResult result) {

        CommonUtil.handleValidMessage(result);

        if (staffOrginfoService.putStaffIntoOrg(dto, false, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("转入失败");
        }
    }

    @ApiOperation("转出人员")
    @Log(description = "转出人员", type = "人员管理")
    @PostMapping("/putoutStaff")
    public JsonResponse putoutStaff(@Valid @RequestBody OrgPutinStaffDTO dto, BindingResult result) {

        CommonUtil.handleValidMessage(result);

        if (staffOrginfoService.putStaffIntoOrg(dto, true, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("转入失败");
        }
    }

}
