package com.hzb.erp.adminCenter.controller;

import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.base.annotation.PreventMultiSubmit;
import com.hzb.erp.common.entity.CreditMall;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.common.pojo.dto.ClassSaveDTO;
import com.hzb.erp.common.pojo.dto.CreditMallParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.ClazzService;
import com.hzb.erp.common.service.CreditMallService;
import com.hzb.erp.common.service.StudentService;
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
 * 积分商城 前端控制器
 * </p>
 *
 * @author 541720500@qq.com
 */
@RestController
@RequestMapping("/common/creditMall")
@Api(value = "积分商城", tags = "积分商城")
public class CreditController {

    @Autowired
    private CreditMallService creditMallService;

    @Autowired
    private ClazzService clazzService;

    @ApiOperation("积分商城积分礼品列表")
    @GetMapping("/gifts")
    public PaginationVO gifts(CreditMallParamDTO param) {
        return JsonResponseUtil.paginate(creditMallService.getList(param));
    }

    @ApiOperation("创建和修改积分礼品")
    @Log(description = "创建和修改积分礼品", type = "积分礼品管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody CreditMall saveDTO, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (creditMallService.saveOrUpdateByDTO(saveDTO)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("提交失败");
        }
    }

    @ApiOperation("删除积分礼品")
    @Log(description = "删除积分礼品", type = "积分礼品管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (creditMallService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("启用积分礼品")
    @Log(description = "启用积分礼品", type = "积分礼品管理")
    @PostMapping("/open")
    public JsonResponse open(@RequestBody List<Long> ids) {
        if (creditMallService.switchState(ids, SwitchEnum.YES)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("禁用积分礼品")
    @Log(description = "禁用积分礼品", type = "积分礼品管理")
    @PostMapping("/close")
    public JsonResponse close(@RequestBody List<Long> ids) {
        if (creditMallService.switchState(ids, SwitchEnum.NO)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }
}
