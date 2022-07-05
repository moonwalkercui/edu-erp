package com.hzb.erp.api.mobile.teacher.controller;

import com.hzb.erp.api.pc.clazz.pojo.ClassParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassVO;
import com.hzb.erp.common.pojo.PaginationVO;
import com.hzb.erp.api.pc.clazz.service.ClazzService;
import com.hzb.erp.api.mobile.teacher.service.TClassService;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/tCenter/class")
@Api(value = "班级数据", tags = "班级数据")
public class TClassController {
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private TClassService tClassService;

    @ApiOperation("班级信息")
    @GetMapping("/info")
    public ClassVO info(@RequestParam(value = "id") Long id) {
        return clazzService.getInfo(id);
    }

    @ApiOperation("课程列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) {
        ClassParamDTO param = new ClassParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        return JsonResponseUtil.paginate(tClassService.getList(param));
    }

}
