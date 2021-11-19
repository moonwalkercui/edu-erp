package com.hzb.erp.adminCenter;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.entity.GradeRecord;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.pojo.dto.GradeParamDTO;
import com.hzb.erp.common.pojo.dto.GradeRecordSaveDTO;
import com.hzb.erp.common.pojo.dto.GradeSaveDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.GradeRecordService;
import com.hzb.erp.common.service.GradeService;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.service.ImportExportService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * 成绩单 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/grade")
@Api(value = "成绩单", tags = "成绩单")
public class GradeController {
    @Autowired
    private GradeService gradeService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeRecordService gradeRecordService;
    @Autowired
    private ImportExportService importExportService;

    @ApiOperation("成绩单")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page, // 若为null则是查全部
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "title", defaultValue = "") String title,
                       @RequestParam(value = "creator", defaultValue = "") Long creator) {
        GradeParamDTO param = new GradeParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setTitle(title);
        param.setCreator(creator);

        return page != null && page > 0 ?
                JsonResponseUtil.paginate(gradeService.getList(param)) :
                gradeService.getAll(param);
    }

    @ApiOperation("创建和修改成绩单")
    @Log(description = "创建和修改成绩单", type = "成绩管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody GradeSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        gradeService.saveOrUpdateByDTO(dto);
        return JsonResponseUtil.success();
    }

    @ApiOperation("删除成绩单")
    @Log(description = "删除成绩单", type = "成绩管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (gradeService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("成绩列表")
    @GetMapping("/recordList")
    public PaginationVO recordList(@RequestParam(value = "page", defaultValue = "") Integer page, // 若为null则是查全部
                                   @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                                   @RequestParam(value = "gradeId", defaultValue = "") Long gradeId) {
        GradeParamDTO param = new GradeParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setGradeId(gradeId);

        return JsonResponseUtil.paginate(gradeRecordService.getList(param));
    }


    @ApiOperation("创建和修改成绩记录")
    @Log(description = "创建和修改成绩记录", type = "成绩管理")
    @PostMapping("/saveRecord")
    @PreventMultiSubmit
    public JsonResponse saveRecord(@Valid @RequestBody GradeRecordSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        gradeRecordService.saveOrUpdateByDTO(dto);
        return JsonResponseUtil.success();
    }

    @ApiOperation("删除成绩记录")
    @Log(description = "删除成绩记录", type = "成绩管理")
    @PostMapping("/deleteRecord")
    public JsonResponse deleteRecord(@RequestBody List<Long> ids) {
        if (gradeRecordService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("导出成绩单模板")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(new HashMap<String, Object>() {{
            put("name", "王同学(例子:导入时请把该行删除)");
            put("id", "");
            put("score", "90");
        }});
        importExportService.exportExcel(response, excelHeaderMap(), list, "成绩单导入模板");
    }

    @ApiOperation("导入成绩单")
    @Log(description = "导入成绩单", type = "成绩管理")
    @PostMapping("/import")
    public JsonResponse importExcel(@RequestParam("file") MultipartFile file, @RequestParam(value = "gradeId") Long gradeId) {

        List<Map<String, Object>> importDate = importExportService.importExcel(file, excelHeaderMap());
        if (importDate == null || importDate.size() == 0) {
            return JsonResponseUtil.error("未导入数据");
        }

        List<GradeRecord> records = new ArrayList<>();

        Student student;
        for (Map<String, Object> map : importDate) {

            List<Student> findList = studentService.listByName((String) map.get("name"));
            if (findList==null || findList.size() == 0) {
                throw new BizException("学生不存在:" + map.get("name"));
            }
            if (findList.size() == 1) {
                student = findList.get(0);
            } else {
                Long studentId;
                try {
                    studentId = (Long) map.get("id");
                } catch (Exception e) {
                    throw new BizException("学生编号格式输入错误");
                }
                if(studentId == null) {
                    throw new BizException("因学生有重名身份无法确认，需要输入学生编号。学生姓名:" + map.get("name"));
                }
                student = studentService.getById(studentId);
            }

            QueryWrapper<GradeRecord> qw = new QueryWrapper<>();
            qw.eq("grade_id", gradeId).eq("student_id", student.getId());
            gradeRecordService.remove(qw);

            GradeRecord item = new GradeRecord();
            item.setGradeId(gradeId);
            item.setStudentId(student.getId());
            item.setScore(Integer.valueOf(map.get("score").toString()));
            records.add(item);
        }
        return gradeRecordService.saveBatch(records) ?
                JsonResponseUtil.success("成功导入" + importDate.size() + "条记录") :
                JsonResponseUtil.error("导入出错");
    }

    private Map<String, String> excelHeaderMap() {
        return new LinkedHashMap<String, String>() {{
            put("name", "*学员姓名");
            put("id", "学生编号(选填)");
            put("score", "*分数");
        }};
    }

}
