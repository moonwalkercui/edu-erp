package com.hzb.erp.api.pc.clazz.controller;


import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.base.annotation.PreventMultiSubmit;
import com.hzb.erp.api.pc.clazz.pojo.ClassroomSaveDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassroomParamDTO;
import com.hzb.erp.common.pojo.PaginationVO;
import com.hzb.erp.api.pc.clazz.service.ClassroomService;
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
 * 教室 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/classroom")
@Api(value = "教室管理", tags = "教室管理")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @ApiOperation("教室列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "name", defaultValue = "") String name) {
        ClassroomParamDTO param = new ClassroomParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setName(name);
        return JsonResponseUtil.paginate(classroomService.getList(param));
    }

    @ApiOperation("创建和修改教室")
    @Log(description = "创建和修改教室", type = "教室管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody ClassroomSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (classroomService.saveOrUpdateByDTO(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("删除教室")
    @Log(description = "删除教室", type = "教室管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (classroomService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

}
