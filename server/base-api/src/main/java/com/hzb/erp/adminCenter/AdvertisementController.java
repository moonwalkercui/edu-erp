package com.hzb.erp.adminCenter;

import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.pojo.dto.AdvertisementParamDTO;
import com.hzb.erp.common.pojo.dto.AdvertisementSaveDTO;
import com.hzb.erp.common.service.AdvertisementService;
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
 * 公告管理 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/advertisement")
@Api(value = "公告管理", tags = "公告管理")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @ApiOperation("公告")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page, // 若为null则是查全部
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "title", defaultValue = "") String title,
                       @RequestParam(value = "creator", defaultValue = "") Long creator) {
        AdvertisementParamDTO param = new AdvertisementParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setTitle(title);
        param.setCreator(creator);

        return page != null && page > 0 ?
                JsonResponseUtil.paginate(advertisementService.getList(param)) :
                advertisementService.getAll(param);
    }

    @ApiOperation("创建和修改公告")
    @Log(description = "创建和修改公告", type = "公告管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody AdvertisementSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        advertisementService.saveOrUpdateByDTO(dto);
        return JsonResponseUtil.success();
    }

    @ApiOperation("删除公告")
    @Log(description = "删除公告", type = "公告管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (advertisementService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("启用公告")
    @Log(description = "启用公告", type = "公告管理")
    @PostMapping("/open")
    public JsonResponse open(@RequestBody List<Long> ids) {
        if (advertisementService.changeState(ids, true)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("停用公告")
    @Log(description = "停用公告", type = "公告管理")
    @PostMapping("/close")
    public JsonResponse close(@RequestBody List<Long> ids) {
        if (advertisementService.changeState(ids, false)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }
}
