package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.common.enums.FinanceStateEnum;
import com.hzb.erp.common.enums.FinanceTypeEnum;
import com.hzb.erp.common.pojo.dto.FinanceParamDTO;
import com.hzb.erp.common.pojo.dto.IdsAndContentDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.FinanceRecordService;
import com.hzb.erp.adminCenter.service.UserAuthService;
import com.hzb.erp.utils.EnumTools;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 财务记录表 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/financeRecord")
@Api(value = "财务记录", tags = "财务记录")
public class FinanceRecordController {

    @Autowired
    private FinanceRecordService financeRecordService;

    @ApiOperation("财务记录列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "type", defaultValue = "") String[] type,
                             @RequestParam(value = "operator", defaultValue = "") Long operator,
                             @RequestParam(value = "payer", defaultValue = "") Long payer,
                             @RequestParam(value = "state", defaultValue = "") String state,
                             @RequestParam(value = "export", defaultValue = "false") Boolean export,
                             @RequestParam(value = "title", defaultValue = "") String title) {
        FinanceParamDTO param = new FinanceParamDTO();
        param.setOperator(operator);
        param.setPayer(payer);
        param.setTitle(title);
        param.setPage(page);
        param.setPageSize(pageSize);

        FinanceStateEnum stateEnum = EnumTools.getByDist(state, FinanceStateEnum.class);
        if (stateEnum != null) {
            param.setState(stateEnum.getCode());
        }

        List<Integer> types = new ArrayList<>();
        for (String s : type) {
            FinanceTypeEnum enumClass = EnumTools.getByDist(s, FinanceTypeEnum.class);
            if (enumClass != null) {
                types.add(enumClass.getCode());
            }
        }
        param.setType(types);

        if (export != null && export) {
            financeRecordService.exportExcel(param);
            return null;
        }

        return JsonResponseUtil.paginate(financeRecordService.getList(param));
    }

    @ApiOperation("审核通过")
    @Log(description = "审核通过", type = "财务管理")
    @PostMapping("/pass")
    public JsonResponse pass(@RequestBody List<Long> ids) {
        if (financeRecordService.changeState(ids, FinanceStateEnum.PASS, null, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作出错");
        }
    }

    @ApiOperation("审核驳回")
    @Log(description = "审核驳回", type = "财务管理")
    @PostMapping("/reject")
    public JsonResponse reject(@RequestBody IdsAndContentDTO dto) {
        if (financeRecordService.changeState(dto.getIds(), FinanceStateEnum.REJECT, dto.getContent(), UserAuthService.getCurrentUserId())) {
            financeRecordService.makeLessonCountLog(dto.getIds(), FinanceStateEnum.REJECT, UserAuthService.getCurrentUserId());
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作出错");
        }
    }
}
