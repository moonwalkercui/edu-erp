package com.hzb.erp.adminCenter;

import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.pojo.dto.CommonParamDTO;
import com.hzb.erp.common.pojo.dto.NoticeValidDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.NoticeService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 公告控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private SystemConfig systemConfig;
    @ApiOperation("公告列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "title", defaultValue = "") String title) {
        CommonParamDTO param = new CommonParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setTitle(title);
        return JsonResponseUtil.paginate(noticeService.getList(param));
    }

    @ApiOperation("创建和修改公告")
    @Log(description = "创建和修改公告", type = "公告管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody NoticeValidDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        if (noticeService.saveOrUpdateByDTO(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除公告")
    @Log(description = "删除公告", type = "公告管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        if (noticeService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

}
