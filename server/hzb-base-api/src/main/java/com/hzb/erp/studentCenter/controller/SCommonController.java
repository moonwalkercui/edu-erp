package com.hzb.erp.studentCenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.entity.Advertisement;
import com.hzb.erp.common.entity.Help;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.entity.WxAccess;
import com.hzb.erp.common.pojo.dto.ChangePasswordDTO;
import com.hzb.erp.common.service.AdvertisementService;
import com.hzb.erp.common.service.HelpService;
import com.hzb.erp.common.service.UserService;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.service.FileService;
import com.hzb.erp.service.UserAuthService;
import com.hzb.erp.service.bo.UploadResultBO;
import com.hzb.erp.service.bo.UploadValidateBO;
import com.hzb.erp.studentCenter.pojo.dto.ChangePasswordFormDTO;
import com.hzb.erp.studentCenter.pojo.vo.UserVo;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;

import com.hzb.erp.wechat.service.WxAccessService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/sCenter")
@Api(value = "通用接口", tags = "通用接口")
public class SCommonController {
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private FileService fileService;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private HelpService helpService;

    @Autowired
    private UserService userService;

    @Autowired
    private WxAccessService wxAccessService;

    @PostMapping("/upload")
    @ResponseBody
    public UploadResultBO upload(@RequestParam("file") MultipartFile file) throws FileUploadException {
        UploadValidateBO validateBO = new UploadValidateBO();
        validateBO.setMaxSize(systemConfig.getUploadImgMaxSize());
        validateBO.setIsImage(true);
        return fileService.upload(file, validateBO, null);
    }

    @GetMapping("/advertisement")
    @ResponseBody
    public List<Advertisement> advertisement() {
        QueryWrapper<Advertisement> qw = new QueryWrapper<>();
        qw.eq("state", 1).orderByDesc("sort_num");
        return advertisementService.list(qw);
    }

    @GetMapping("/help")
    @ResponseBody
    public IPage<Help> help(@RequestParam(value = "page", defaultValue = "") Integer page,
                            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) {
        QueryWrapper<Help> qw = new QueryWrapper<>();
        qw.eq("state", 1).orderByDesc("sort_num");
        return helpService.page(new Page<Help>(page, pageSize), qw);
    }

    @GetMapping("/helpInfo")
    @ResponseBody
    public Help helpInfo(@RequestParam(value = "id") Long id) {
        return helpService.getById(id);
    }

    @GetMapping("/getCurrentUserInfo")
    @ResponseBody
    public UserVo getCurrentUserInfo() {
        User user = userService.getById(UserAuthService.getCurrentUserId());
        UserVo currentUser = new UserVo();
        currentUser.setMobile(user.getMobile());
        currentUser.setName(user.getName());
        currentUser.setAddTime(user.getAddTime());

        WxAccess wxaccess = wxAccessService.getById(user.getWxAccessId());
        if (wxaccess != null) {
            currentUser.setNickname(wxaccess.getNickname());
            currentUser.setHeadImg(wxaccess.getHeadImg());
        }
        return currentUser;
    }

    @PostMapping("/updateUserInfo")
    @ResponseBody
    public JsonResponse updateUserInfo(@Valid @RequestBody UserVo dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);

        User user = userService.getById(UserAuthService.getCurrentUserId());
        if (StringUtils.isNotBlank(dto.getName())) {
            user.setName(dto.getName());
        }
        if (userService.updateById(user)) {
            return JsonResponseUtil.success("已修改");
        } else {
            return JsonResponseUtil.error();
        }
    }

    @PostMapping("/changePw")
    @ResponseBody
    public JsonResponse changePw(@Valid @RequestBody ChangePasswordFormDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);

        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("测试账号不能操作");
        }

        String oldpw = dto.getOldpassword().trim();
        String newpw = dto.getNewpassword().trim();
        String repw = dto.getRepassword().trim();

        if (newpw.equals(oldpw)) {
            return JsonResponseUtil.error("新密码和旧密码不能一样");
        }

        if (!newpw.equals(repw)) {
            return JsonResponseUtil.error("两次新密码输入不一致");
        }

        ChangePasswordDTO updateDto = new ChangePasswordDTO();
        updateDto.setId(UserAuthService.getCurrentUserId());
        updateDto.setPassword(newpw);
        updateDto.setPasswordEncode(SecurityUtils.passwordEncode(newpw));

        if (userService.changPassword(updateDto)) {
            return JsonResponseUtil.success("已修改");
        } else {
            return JsonResponseUtil.error();
        }
    }

}
