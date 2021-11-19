package com.hzb.erp.adminCenter;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.pojo.dto.HomeworkParamDTO;
import com.hzb.erp.common.pojo.dto.HomeworkRecordCommentSaveDTO;
import com.hzb.erp.common.pojo.dto.HomeworkSaveDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.HomeworkRecordService;
import com.hzb.erp.common.service.HomeworkService;
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
 * 作业 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/homework")
@Api(value = "作业", tags = "作业")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private HomeworkRecordService homeworkRecordService;

    @ApiOperation("作业列表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page, // 若为null则是查全部
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "title", defaultValue = "") String title,
                       @RequestParam(value = "classId", defaultValue = "") Long classId,
                       @RequestParam(value = "teacherId", defaultValue = "") Long teacherId) {
        HomeworkParamDTO param = new HomeworkParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setClassId(classId);
        param.setTeacherId(teacherId);
        param.setTitle(title);

        return page != null && page > 0 ?
                JsonResponseUtil.paginate(homeworkService.getList(param)) :
                homeworkService.getAll(param);
    }

    @ApiOperation("创建和修改作业")
    @Log(description = "创建和修改作业", type = "作业管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody HomeworkSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        homeworkService.saveOrUpdateByDTO(dto);
        return JsonResponseUtil.success();
    }

    @ApiOperation("删除作业")
    @Log(description = "删除作业", type = "作业管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (homeworkService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("作业提交列表")
    @GetMapping("/recordsList")
    public PaginationVO recordsList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
            @RequestParam(value = "homeworkId", defaultValue = "") Long homeworkId) {

        HomeworkParamDTO param = new HomeworkParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setHomeworkId(homeworkId);

        return JsonResponseUtil.paginate(homeworkRecordService.getList(param));
    }

    @ApiOperation("作业评价")
    @Log(description = "作业评价", type = "作业管理")
    @PostMapping("/recordComment")
    public JsonResponse recordComment(@Valid @RequestBody HomeworkRecordCommentSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (homeworkRecordService.comment(dto, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }


}
