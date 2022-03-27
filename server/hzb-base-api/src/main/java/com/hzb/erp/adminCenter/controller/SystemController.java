package com.hzb.erp.adminCenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.entity.Setting;
import com.hzb.erp.common.entity.SettingNotice;
import com.hzb.erp.common.entity.SettingOption;
import com.hzb.erp.common.mapper.SysLogMapper;
import com.hzb.erp.common.pojo.dto.SysLogParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.SettingNoticeService;
import com.hzb.erp.common.service.SettingOptionService;
import com.hzb.erp.common.service.SettingService;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 系统管理控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/sys")
@Api(value = "系统管理", tags = "系统管理")
public class SystemController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private SettingOptionService settingOptionService;
    @Autowired
    private SettingNoticeService settingNoticeService;
    @Resource
    private SysLogMapper sysLogMapper;

    @ApiOperation("设置列表")
    @GetMapping("/settingList")
    public List<Setting> settingList() {
        QueryWrapper<Setting> qw = new QueryWrapper<>();
        qw.orderByDesc("sort_num");
        return settingService.list(qw);
    }

    @ApiOperation("创建和修改配置")
    @Log(description = "创建和修改配置", type = "系统管理")
    @PostMapping("/saveSetting")
    @PreventMultiSubmit
    public JsonResponse saveSetting(@RequestBody Setting setting) {
        if (StringUtils.isBlank(setting.getName())) {
            return JsonResponseUtil.error("缺少配置名称");
        }
        boolean res;
        setting.setName(StringUtils.trim(setting.getName()));
        setting.setCode(StringUtils.trim(setting.getCode()));
        if (setting.getId() != null) {
            res = settingService.updateById(setting);
        } else {
            res = settingService.save(setting);
        }
        return res ? JsonResponseUtil.success("已保存") : JsonResponseUtil.error("操作失败");
    }

    @ApiOperation("设置项列表")
    @GetMapping("/settingOptions")
    public Object settingOptions(@RequestParam(value = "page", defaultValue = "") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                                 @RequestParam(value = "id") Long id) {
        QueryWrapper<SettingOption> qw = new QueryWrapper<>();
        qw.eq("setting_id", id).orderByDesc("sort_num");
        return page != null
                ? JsonResponseUtil.paginate(settingOptionService.page(new Page<>(page, pageSize), qw))
                : settingOptionService.list(qw);
    }

    @ApiOperation("创建和修改配置项")
    @Log(description = "创建和修改配置项", type = "系统管理")
    @PostMapping("/saveSettingOption")
    @PreventMultiSubmit
    @CacheEvict(value = {"SettingCache", "SettingOptionList"})
    public JsonResponse saveSettingOption(@RequestBody SettingOption option) {
        System.out.println(option);
        if (StringUtils.isBlank(option.getName())) {
            return JsonResponseUtil.error("缺少配置名称");
        }
        boolean res;
        option.setName(StringUtils.trim(option.getName()));
        option.setCode(StringUtils.trim(option.getCode()));
        if (option.getId() != null) {
            res = settingOptionService.updateById(option);
        } else {
            res = settingOptionService.save(option);
        }
        return res ? JsonResponseUtil.success("已保存") : JsonResponseUtil.error("操作失败");
    }

    @ApiOperation("通知设置列表")
    @GetMapping("/settingNoticeList")
    public List<SettingNotice> settingNoticeList(@RequestParam(value = "group") String group) {
        QueryWrapper<SettingNotice> qw = new QueryWrapper<>();
        qw.eq("group_code", group).orderByDesc("sort_num");
        return settingNoticeService.list(qw);
    }

    @ApiOperation("通知设置修改")
    @Log(description = "通知设置修改", type = "系统管理")
    @GetMapping("/settingNoticeSave")
    public JsonResponse settingNoticeSave(@RequestParam(value = "id") String id,
                                          @RequestParam(value = "type") String type,
                                          @RequestParam(value = "code", defaultValue = "") String code,
                                          @RequestParam(value = "value", defaultValue = "") Boolean value) {
        SettingNotice sn = settingNoticeService.getById(id);
        if (sn == null) {
            return JsonResponseUtil.error("未知配置项");
        }
        if (value != null) {
            if ("wx".equals(type)) {
                sn.setWxOn(value);
            } else if ("email".equals(type)) {
                sn.setEmailOn(value);
            } else {
                sn.setSmsOn(value);
            }
        }
        if (StringUtils.isNotBlank(code)) {
            if ("wx".equals(type)) {
                sn.setWxCode(code);
            } else {
                sn.setSmsCode(code);
            }
        }

        return settingNoticeService.updateById(sn) ? JsonResponseUtil.success("已保存") : JsonResponseUtil.error("操作失败");
    }

    @ApiOperation("操作日志")
    @GetMapping("/sysLog")
    public PaginationVO sysLog(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                               @RequestParam(value = "keyword", defaultValue = "") String keyword,
                               @RequestParam(value = "type", defaultValue = "") String type,
                               @RequestParam(value = "operator", defaultValue = "") Long operator,
                               @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                               @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        SysLogParamDTO param = new SysLogParamDTO();
        param.setOperator(operator);
        param.setKeyword(keyword);
        param.setType(type);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        return JsonResponseUtil.paginate(sysLogMapper.getList(new Page<>(page, pageSize), param));

    }

}
