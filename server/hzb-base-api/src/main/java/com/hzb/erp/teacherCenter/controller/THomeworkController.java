package com.hzb.erp.teacherCenter.controller;

import com.hzb.erp.annotation.Log;
import com.hzb.erp.common.pojo.dto.HomeworkParamDTO;
import com.hzb.erp.common.pojo.dto.HomeworkRecordCommentSaveDTO;
import com.hzb.erp.common.pojo.vo.HomeworkVO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.HomeworkRecordService;
import com.hzb.erp.common.service.HomeworkService;
import com.hzb.erp.service.UserAuthService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/tCenter/homework")
@Api(value = "作业", tags = "作业")
public class THomeworkController {

    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private HomeworkRecordService homeworkRecordService;

    @ApiOperation("作业信息")
    @GetMapping("/info")
    public HomeworkVO info(@RequestParam("id") Long id) {
        return homeworkService.getInfo(id, null);
    }


    @ApiOperation("作业列表")
    @GetMapping("/list")
    public PaginationVO list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) {

        HomeworkParamDTO param = new HomeworkParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setTeacherId(UserAuthService.getCurrentUserId());

        return JsonResponseUtil.paginate(homeworkService.getList(param));
    }

    @ApiOperation("作业评价")
    @Log(description = "作业评价", type = "作业管理")
    @PostMapping("/comment")
    public JsonResponse comment(@Valid @RequestBody HomeworkRecordCommentSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (homeworkRecordService.comment(dto, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

}
