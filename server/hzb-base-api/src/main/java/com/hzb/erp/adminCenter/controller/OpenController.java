package com.hzb.erp.adminCenter.controller;

import com.hzb.erp.base.annotation.PreventMultiSubmit;
import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.service.AdvertisementService;
import com.hzb.erp.service.CaptchaManager;
import com.hzb.erp.service.SmsManager;
import com.hzb.erp.service.dto.SmsSendDTO;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Ryan
 * 开放的接口，不受登录验证
 **/
@RestController
@RequestMapping("/common/open")
@Api(value = "开放的接口", tags = "开放的接口")
public class OpenController {

    @Resource
    private CaptchaManager captchaManager;

    @Resource
    private SmsManager smsManager;

    @Resource
    private AdvertisementService advertisementService;

    @Resource
    private SystemConfig systemConfig;

    @GetMapping("/")
    public String test() {
        return "HZB-ERP BOOT SUCCESSFUL!!!";
    }

    @ApiOperation("版本号判断 大于0表示有新版")
    @GetMapping("/version/{ver}")
    public Integer version(@PathVariable String ver) {
        return CommonUtil.versionCompare(systemConfig.getVersion(), ver);
    }

    @ApiOperation("验证码")
    @GetMapping("/capture")
    public void capture(@RequestParam(value = "uuid") String uuid) {
        captchaManager.writeCode(uuid);
    }

    @ApiOperation("发送短信验证码")
    @PostMapping("/sendSms")
    @PreventMultiSubmit
    public JsonResponse sendSms(@Valid @RequestBody SmsSendDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);

        String code = smsManager.makeSmsCode();
        dto.setContent(code);

        return smsManager.sendCode(dto) ?
                JsonResponseUtil.success(code) :
                JsonResponseUtil.error("短信发送出错");
    }

//    @ApiOperation("验证验证码")
//    @GetMapping("/captureValid")
//    public void capture(@RequestParam(value = "uuid") String uuid, @RequestParam(value = "code") String code) {
//        System.out.println(captchaManager.valid(code, uuid));
//    }

    @ApiOperation("获取uuid")
    @GetMapping("/getUuid")
    public String getUuid() {
        return CommonUtil.generateUUID();
    }

    @ApiOperation("获取文章")
    @GetMapping("/article")
    public Object article(@RequestParam(value = "id", defaultValue = "") Long id,
                          @RequestParam(value = "code", defaultValue = "") String code) {
        if (id != null) {
            return advertisementService.getById(id);
        } else if (StringUtils.isNotBlank(code)) {
            return advertisementService.getByCode(code);
        } else {
            return JsonResponseUtil.error("参数错误");
        }
    }
}
