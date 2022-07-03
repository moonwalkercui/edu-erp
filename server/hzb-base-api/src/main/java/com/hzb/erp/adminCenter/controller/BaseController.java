package com.hzb.erp.adminCenter.controller;

import com.hzb.erp.common.entity.Region;
import com.hzb.erp.common.mapper.CommonMapper;
import com.hzb.erp.common.mapper.RegionMapper;
import com.hzb.erp.common.pojo.vo.AutocompleteBuilderVO;
import com.hzb.erp.common.pojo.vo.SelectBuilderVO;
import com.hzb.erp.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * description UI数据接口
 **/
@RestController
@RequestMapping("/common/base")
@Api(value = "UI数据接口", tags = "UI数据接口")
public class BaseController {
    @Resource
    private CommonMapper commonMapper;

    @Resource
    private RegionMapper regionMapper;

    /**
     * 下拉菜单自动记载
     * 返回的结果里只包含id和label两列数据
     */
    @ApiOperation("下拉选项列表")
    @GetMapping("/selectBuilder")
    public List<SelectBuilderVO> selectBuilder(@RequestParam("code") String code) {
        return commonMapper.selectBuilder(code);
    }

    @ApiOperation("加载选择器label")
    @GetMapping("/loadSelectorLabel")
    public String loadSelectorLabel(@RequestParam("code") String code,
                                    @RequestParam("ids") String[] ids) {
        return commonMapper.loadSelectorLabel(code, ids);
    }

    @ApiOperation("数据字典下拉选项列表")
    @GetMapping("/dictBuilder")
    public List<SelectBuilderVO> dictBuilder(@RequestParam("code") String code) {
        return commonMapper.dictBuilder(code);
    }

    @ApiOperation("自动推荐输入框")
    @GetMapping("/aotucompleteBuilder")
    public List<AutocompleteBuilderVO> aotucompleteBuilder(@RequestParam("code") String code, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        if (keyword == null) {
            return new ArrayList<>();
        }
        List<AutocompleteBuilderVO> res = commonMapper.aotucomplateBuilder(code, keyword);
        return res == null ? new ArrayList<>() : res;
    }

    @ApiOperation("码值列表")
    @GetMapping("/enumList")
    public Map<String, Map<Object, String>> enumList() throws Exception {
        return CommonService.enumList();
    }

    @ApiOperation("地区加载器")
    @GetMapping("/region")
    public List<Region> region(@RequestParam("pid") Integer pid) {
        return regionMapper.getByPid(pid);
    }

}
