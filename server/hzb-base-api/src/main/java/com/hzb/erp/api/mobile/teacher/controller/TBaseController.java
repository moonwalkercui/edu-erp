package com.hzb.erp.api.mobile.teacher.controller;

import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.api.pc.sys.mapper.CommonMapper;
import com.hzb.erp.common.pojo.StaffVO;
import com.hzb.erp.common.service.StaffService;
import com.hzb.erp.configuration.SystemConfig;
import com.hzb.erp.service.FileService;
import com.hzb.erp.api.base.service.UserAuthService;
import com.hzb.erp.service.bo.UploadResultBO;
import com.hzb.erp.service.bo.UploadValidateBO;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/tCenter/base")
@Api(value = "基础数据", tags = "基础数据")
public class TBaseController {

    @Resource
    private CommonMapper commonMapper;

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private FileService fileService;

    @Resource
    private StaffService staffService;

    @ApiOperation("登录信息")
    @GetMapping("/getLoginStaff")
    public StaffVO getLoginStaff() {
        return UserAuthService.getLoginStaff();
    }

    @ApiOperation("首页统计数据")
    @GetMapping("/getHomeCounts")
    public Map<String, Long> getHomeCounts() {
        return commonMapper.teacherCenterStatsCount(UserAuthService.getCurrentUserId());
    }

    @PostMapping("/uploadAvatar")
    @ResponseBody
    public JsonResponse uploadAvatar(@RequestParam("file") MultipartFile file) throws FileUploadException {
        UploadValidateBO validateBO = new UploadValidateBO();
        validateBO.setMaxSize(systemConfig.getUploadImgMaxSize());
        validateBO.setIsImage(true);
        UploadResultBO uploadResult = fileService.upload(file, validateBO, null);

        Staff staff = staffService.getById(UserAuthService.getCurrentUserId());
        staff.setHeadImg(uploadResult.getUrl());

        return staffService.updateById(staff) ?
                JsonResponseUtil.success() :
                JsonResponseUtil.error();
    }
}
