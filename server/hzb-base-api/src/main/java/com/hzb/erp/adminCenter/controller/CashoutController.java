package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.mapper.CashoutMapper;
import com.hzb.erp.common.pojo.dto.CashoutParamDTO;
import com.hzb.erp.common.pojo.dto.CashoutSaveDTO;
import com.hzb.erp.common.pojo.dto.IdsAndContentDTO;
import com.hzb.erp.common.pojo.vo.CashoutVO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.CashoutService;
import com.hzb.erp.service.UserAuthService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.EnumTools;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 请跨控制器
 * </p>
 *
 * @author 541720500@qq.com
 */
@RestController
@RequestMapping("/common/cashout")
@Api(value = "请款管理", tags = "请款管理")
public class CashoutController {

    @Autowired
    private CashoutService cashoutService;

    @Autowired
    private CashoutMapper cashoutMapper;

    @ApiOperation("请款列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "state", defaultValue = "") String state,
                             @RequestParam(value = "payee", defaultValue = "") String payee,
                             @RequestParam(value = "type", defaultValue = "") Long type,
                             @RequestParam(value = "staffId", defaultValue = "") Long staffId,
                             @RequestParam(value = "export", defaultValue = "false") Boolean export,
                             @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                             @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        CashoutParamDTO param = new CashoutParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStartDate(startDate);
        if (endDate != null) {
            param.setEndDate(endDate.plusDays(1));
        }
        param.setPayee(payee);
        param.setStaffId(staffId);
        param.setType(type);
        if (StringUtils.isNotBlank(state)) {
            VerifyStateEnum stageEnum = EnumTools.getByDist(state, VerifyStateEnum.class);
            if (stageEnum != null) {
                param.setVerifyState(stageEnum.getCode());
            }
        }
        if (export != null && export) {
            cashoutService.exportExcel(param);
            return null;
        }
        return JsonResponseUtil.paginate(cashoutService.getList(param));
    }

    @ApiOperation("我的请款列表")
    @GetMapping("/mine")
    public PaginationVO mine(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "state", defaultValue = "") String state,
                             @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                             @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        CashoutParamDTO param = new CashoutParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        param.setStaffId(UserAuthService.getCurrentUserId());
        if (StringUtils.isNotBlank(state)) {
            VerifyStateEnum stageEnum = EnumTools.getByDist(state, VerifyStateEnum.class);
            if (stageEnum != null) {
                param.setVerifyState(stageEnum.getCode());
            }
        }
        return JsonResponseUtil.paginate(cashoutService.getList(param));
    }

    @ApiOperation("请款信息")
    @GetMapping("/info")
    public CashoutVO info(@RequestParam(value = "id") Long id) {
        return cashoutMapper.getInfo(id);
    }

    @ApiOperation("创建和修改请款")
    @Log(description = "创建和修改请款", type = "请款管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody CashoutSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (cashoutService.saveOrUpdateByDTO(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("作废请款")
    @Log(description = "作废请款", type = "请款管理")
    @PostMapping("/delete/{id}")
    @PreventMultiSubmit
    public JsonResponse cancel(@PathVariable Long id) {
        if (cashoutService.cancel(id)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("认款通过")
    @Log(description = "认款通过", type = "请款管理")
    @PostMapping("/pass")
    public JsonResponse pass(@RequestBody List<Long> ids) {
        if (cashoutService.changeState(ids, VerifyStateEnum.APPROVE, null, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作出错");
        }
    }

    @ApiOperation("认款驳回")
    @Log(description = "认款驳回", type = "请款管理")
    @PostMapping("/reject")
    public JsonResponse reject(@RequestBody IdsAndContentDTO dto) {
        if (cashoutService.changeState(dto.getIds(), VerifyStateEnum.REJECT, dto.getContent(), UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作出错");
        }
    }
}
