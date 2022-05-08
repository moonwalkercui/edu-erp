package com.hzb.erp.studentMobile.controller;

import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.service.UserService;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.service.FileService;
import com.hzb.erp.service.SmsManager;
import com.hzb.erp.service.enums.SmsSceneType;
import com.hzb.erp.studentMobile.pojo.dto.ForgetPasswordDTO;
import com.hzb.erp.studentMobile.pojo.dto.UserRegisterDTO;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/sCenter/open")
@Api(value = "开放接口", tags = "开放接口")
public class SOpenController {
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsManager smsManager;

    @PostMapping("/register")
    @ResponseBody
    public JsonResponse register(@Valid @RequestBody UserRegisterDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);

        if (dto.getAgree() == null || !dto.getAgree()) {
            return JsonResponseUtil.error("未同意服务条件，无法注册");
        }

        if (!smsManager.valid(SmsSceneType.STUDENT_REGISTER.getCode(), dto.getMobile(), dto.getSmscode())) {
            return JsonResponseUtil.error("短信验证码错误");
        }

        if (userService.register(dto.getMobile(), SecurityUtils.passwordEncode(dto.getPassword()), dto.getName())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("注册失败");
        }
    }

    @PostMapping("/forgetPwd")
    @ResponseBody
    public JsonResponse forgetPwd(@Valid @RequestBody ForgetPasswordDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (!smsManager.valid(SmsSceneType.STUDENT_FORGETPW.getCode(), dto.getMobile(), dto.getSmscode())) {
            return JsonResponseUtil.error("短信验证码错误");
        }
        if (userService.updatePwdByMob(dto.getMobile(), SecurityUtils.passwordEncode(dto.getPassword()))) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("注册失败");
        }
    }
}
