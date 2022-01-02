package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.entity.ContactRecord;
import com.hzb.erp.common.enums.ContactStageEnum;
import com.hzb.erp.common.pojo.dto.ContactRecordParamDTO;
import com.hzb.erp.common.pojo.dto.ContactRecordSaveDTO;
import com.hzb.erp.common.service.ContactRecordService;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.service.UserAuthService;
import com.hzb.erp.utils.EnumTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * <p>
 * 学员跟进表 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/contactRecord")
@Api(value = "学员跟进管理", tags = "学员跟进管理")
public class ContactRecordController {

    @Autowired
    private ContactRecordService contactRecordService;
    @Autowired
    private StudentService studentService;

    @ApiOperation("联系记录")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "name", defaultValue = "") String name,
                       @RequestParam(value = "studentId", defaultValue = "") Long studentId,
                       @RequestParam(value = "staffId", defaultValue = "") Long staffId,
                       @RequestParam(value = "stage", defaultValue = "") String stage,
                       @RequestParam(value = "self", defaultValue = "false") Boolean self,
                       @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                       @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        ContactRecordParamDTO param = new ContactRecordParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStudentId(studentId);
        param.setEndDate(endDate);
        param.setStartDate(startDate);
        param.setStaffId(staffId);
        param.setStudentName(name);
        if (self != null && self) {
            param.setStaffId(UserAuthService.getCurrentUserId());
        }
        if (StringUtils.isNotBlank(stage)) {
            ContactStageEnum stageEnum = EnumTools.getByDist(stage, ContactStageEnum.class);
            if (stageEnum != null) {
                param.setStage(stageEnum.getCode());
            }
        }
        return JsonResponseUtil.paginate(contactRecordService.getList(param));
    }

    @ApiOperation("保存联系记录")
    @Log(description = "保存联系记录", type = "跟进管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    JsonResponse save(@Valid @RequestBody ContactRecordSaveDTO contactRecordSaveDTO, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (contactRecordService.saveOrUpdateByDTO(contactRecordSaveDTO)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除联系记录")
    @Log(description = "删除联系记录", type = "跟进管理")
    @GetMapping("/delete")
    JsonResponse delete(@RequestParam Long id) {
        ContactRecord one = contactRecordService.getById(id);
        if (!one.getCreator().equals(UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.error("只能删除自己的跟进记录");
        }
        if (contactRecordService.removeById(id)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }


}
