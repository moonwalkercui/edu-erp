package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.base.annotation.PreventMultiSubmit;
import com.hzb.erp.common.pojo.dto.CommonParamDTO;
import com.hzb.erp.common.pojo.dto.SubjectSaveDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.SubjectService;
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
 * 科目表 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/subject")
@Api(value = "科目管理", tags = "科目管理")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("科目列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "name", defaultValue = "") String name) {
        CommonParamDTO param = new CommonParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setName(name);
        return JsonResponseUtil.paginate(subjectService.getList(param));
    }

//    @ApiOperation("课程统计")
//    @GetMapping("/statistic")
//    public PaginationVO statistic(@RequestParam(value = "page", defaultValue = "1") Integer page,
//                            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
//                            @RequestParam(value = "subjectId", defaultValue = "") Long subjectId,
//                            @RequestParam(value = "startDate", defaultValue = "") LocalDate startDate,
//                            @RequestParam(value = "endDate", defaultValue = "") LocalDate endDate) {
//        SubjectParamDTO param = new SubjectParamDTO();
//        param.setPage(page);
//        param.setPageSize(pageSize);
//        param.setSubjectId(subjectId);
//        param.setStartDate(startDate);
//        param.setEndDate(endDate);
//        return ResponseUtils.paginate(subjectService.statisticCounts(param));
//    }

    @ApiOperation("创建和修改科目")
    @Log(description = "创建和修改科目", type = "科目管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody SubjectSaveDTO nameValidDTO, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        subjectService.saveOrUpdateByDTO(nameValidDTO);
        return JsonResponseUtil.success();
    }

    @ApiOperation("删除科目")
    @Log(description = "删除科目", type = "科目管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (subjectService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

}
