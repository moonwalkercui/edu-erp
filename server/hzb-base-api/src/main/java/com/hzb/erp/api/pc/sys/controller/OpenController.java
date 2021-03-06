package com.hzb.erp.api.pc.sys.controller;

import com.hzb.erp.api.base.annotation.PreventMultiSubmit;
import com.hzb.erp.configuration.SystemConfig;
import com.hzb.erp.api.pc.sys.service.AdvertisementService;
import com.hzb.erp.service.CaptchaManager;
import com.hzb.erp.service.SmsManager;
import com.hzb.erp.service.cache.SmsCodeCache;
import com.hzb.erp.service.dto.SmsSendDTO;
import com.hzb.erp.service.enums.SmsSceneType;
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
import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("code", code);
        dto.setDataMap(dataMap);
        // 缓存验证码用于验证
        SmsCodeCache.put(code, dto.getMobile(), SmsSceneType.STUDENT_REGISTER);

        if(systemConfig.getIsDemo()!=null && systemConfig.getIsDemo()) {
            // 超时验证
            smsManager.limitValid(dto.getMobile());
            return JsonResponseUtil.success("模拟发送短信验证码：" + code);
        }

        if(smsManager.sendCode(dto)) {
            return JsonResponseUtil.success("短信已发送");
        } else {
            return JsonResponseUtil.error("短信发送出错");
        }
    }

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
