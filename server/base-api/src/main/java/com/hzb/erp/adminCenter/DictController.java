package com.hzb.erp.adminCenter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.*;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 数据字典控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/dict")
@Api(value = "数据字典", tags = "数据字典")
public class DictController {

    @Resource
    private DictService dictService;
    @Resource
    private DictItemService dictItemService;

    @ApiOperation("设置列表")
    @GetMapping("/list")
    public List<Dict> list() {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.orderByDesc("sort_num");
        return dictService.list(qw);
    }

    @ApiOperation("创建和修改字典项")
    @Log(description = "创建和修改字典项", type = "系统管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@RequestBody Dict dict) {
        if (StringUtils.isBlank(dict.getName())) {
            return JsonResponseUtil.error("缺少配置名称");
        }
        boolean res;
        dict.setName(StringUtils.trim(dict.getName()));
        dict.setCode(StringUtils.trim(dict.getCode()));
        if (dict.getId() != null) {
            res = dictService.updateById(dict);
        } else {
            res = dictService.save(dict);
        }
        return res ? JsonResponseUtil.success("已保存") : JsonResponseUtil.error("操作失败");
    }

    @ApiOperation("字典子项列表")
    @GetMapping("/dictItems")
    public PaginationVO dictItems(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                                  @RequestParam(value = "id") Long id) {
        QueryWrapper<DictItem> qw = new QueryWrapper<>();
        qw.eq("dict_id", id).orderByDesc("sort_num");
        return JsonResponseUtil.paginate(dictItemService.page(new Page<>(page, pageSize), qw));
    }

    @ApiOperation("创建和修改字典子项")
    @Log(description = "创建和修改字典子项", type = "系统管理")
    @PostMapping("/saveItem")
    @PreventMultiSubmit
    public JsonResponse saveItem(@RequestBody DictItem item) {
        System.out.println(item);
        if (StringUtils.isBlank(item.getName())) {
            return JsonResponseUtil.error("缺少配置名称");
        }
        boolean res;
        item.setName(StringUtils.trim(item.getName()));
        if (item.getId() != null) {
            res = dictItemService.updateById(item);
        } else {
            res = dictItemService.save(item);
        }
        return res ? JsonResponseUtil.success("已保存") : JsonResponseUtil.error("操作失败");
    }

    @ApiOperation("删除字典子项")
    @Log(description = "删除字典子项", type = "系统管理")
    @PostMapping("/deleteItem")
    public JsonResponse deleteItem(@RequestBody List<Long> ids) {
        if (dictItemService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }
}
