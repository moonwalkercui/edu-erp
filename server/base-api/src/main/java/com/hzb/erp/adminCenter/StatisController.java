package com.hzb.erp.adminCenter;

import com.hzb.erp.common.mapper.StatsMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 统计控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/statis")
@Api(value = "数据统计", tags = "数据统计")
public class StatisController {

    @Resource
    private StatsMapper statsMapper;

    @ApiOperation("课程总销量")
    @GetMapping("/courseSalesTotal")
    public List courseSalesTotal(@RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return statsMapper.courseSalesTotal(startDate, endDate);
    }

    @ApiOperation("日销量趋势")
    @GetMapping("/courseSaleByDay")
    public List courseSaleByDay(@RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return statsMapper.courseSalesByDate(startDate, endDate);
    }

    @ApiOperation("老师课次排行")
    @GetMapping("/teacherLessonTotal")
    public List teacherLessonTotal(@RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                   @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return statsMapper.teacherLessonTotal(startDate, endDate);
    }

    @ApiOperation("新学员录入趋势")
    @GetMapping("/newStudentCounts")
    public List newStudentCounts(@RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return statsMapper.newStudentCounts(startDate, endDate);
    }

    @ApiOperation("学员课次数排行")
    @GetMapping("/studentLessonCounts")
    public List studentLessonCounts(@RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return statsMapper.studentLessonCounts(startDate, endDate);
    }

    @ApiOperation("控制台数字")
    @GetMapping("/dashboardCounts")
    public Map dashboardCounts() {
        return statsMapper.dashboardCounts();
    }

    @ApiOperation("销售漏斗模型")
    @GetMapping("/funnel")
    public List<Map<String, Object>> funnel() {
        return statsMapper.funnelData();
    }

    @ApiOperation("在学学生年龄统计")
    @GetMapping("/studentAgeStatis")
    public List<Map<String, Object>> studentAgeStatis() {
        return statsMapper.studentAgeStats();
    }

    @ApiOperation("在学学生年龄统计")
    @GetMapping("/teachEvaluationStatis")
    public List<Map<String, Object>> teachEvaluationStatis(@RequestParam(value = "teacherId", defaultValue = "") Long teacherId,
                                                           @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                           @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return statsMapper.teachEvaluationStatis(teacherId, startDate, endDate);
    }

}
