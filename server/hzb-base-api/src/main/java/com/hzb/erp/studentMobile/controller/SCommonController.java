package com.hzb.erp.studentMobile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.AdvertisementTypeEnum;
import com.hzb.erp.common.mapper.UserMapper;
import com.hzb.erp.common.pojo.dto.ChangePasswordDTO;
import com.hzb.erp.common.pojo.dto.GradeParamDTO;
import com.hzb.erp.common.service.*;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.service.FileService;
import com.hzb.erp.service.bo.UploadResultBO;
import com.hzb.erp.service.bo.UploadValidateBO;
import com.hzb.erp.studentMobile.pojo.dto.ChangePasswordFormDTO;
import com.hzb.erp.studentMobile.pojo.vo.UserVo;
import com.hzb.erp.studentMobile.service.StudentAuthService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.wechat.service.WxAccessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SettingOptionService settingOptionService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DictService dictService;


    @ApiOperation("上传图片")
    @PostMapping("/upload")
    @ResponseBody
    public UploadResultBO upload(@RequestParam("file") MultipartFile file) throws FileUploadException {
        UploadValidateBO validateBO = new UploadValidateBO();
        validateBO.setMaxSize(systemConfig.getUploadImgMaxSize());
        validateBO.setIsImage(true);
        return fileService.upload(file, validateBO, null);
    }

    @ApiOperation("首页公告")
    @GetMapping("/advertisement")
    @ResponseBody
    public Map<String, Object> advertisement() {
        QueryWrapper<Advertisement> qw1 = new QueryWrapper<>();
        qw1.eq("state", 1).eq("type", AdvertisementTypeEnum.BANNER.getCode())
                .isNotNull("cover")
                .orderByDesc("sort_num").orderByAsc("id").last("limit 10");

        QueryWrapper<Advertisement> qw2 = new QueryWrapper<>();
        qw2.eq("state", 1).eq("type", AdvertisementTypeEnum.TIP.getCode())
                .orderByDesc("sort_num").orderByAsc("id").last("limit 10");

        QueryWrapper<Advertisement> qw3 = new QueryWrapper<>();
        qw3.eq("state", 1).eq("type", AdvertisementTypeEnum.POPUP.getCode())
                .isNotNull("cover")
                .orderByDesc("sort_num").orderByAsc("id").last("limit 1");

        Map<String, Object> res = new HashMap<>();
        res.put("banner", advertisementService.list(qw1));
        res.put("tip", advertisementService.list(qw2));
        res.put("popup", advertisementService.getOne(qw3));
        return res;
    }

    @ApiOperation("公告列表")
    @GetMapping("/advertisementList")
    @ResponseBody
    public Object advertisementList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        QueryWrapper<Advertisement> qw1 = new QueryWrapper<>();
        qw1.eq("state", 1).orderByDesc("sort_num").orderByAsc("id");
        Page<Advertisement> ipage  = new Page<>(page, pageSize);
        return JsonResponseUtil.paginate(advertisementService.page(ipage, qw1));
    }

    @ApiOperation("获取系统参数")
    @GetMapping("/systemSettings")
    @ResponseBody
    public List<SettingOption> systemSettings(@RequestParam(value = "codes", required = true) String[] codes) {
        QueryWrapper<SettingOption> qw = new QueryWrapper<>();
        qw.in("code", Arrays.asList(codes));
        return settingOptionService.list(qw);
    }

    @ApiOperation("帮助列表")
    @GetMapping("/help")
    @ResponseBody
    public IPage<Help> help(@RequestParam(value = "page", defaultValue = "") Integer page,
                            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        QueryWrapper<Help> qw = new QueryWrapper<>();
        qw.eq("state", 1).orderByDesc("sort_num");
        return helpService.page(new Page<Help>(page, pageSize), qw);
    }

    @ApiOperation("帮助信息")
    @GetMapping("/helpInfo")
    @ResponseBody
    public Help helpInfo(@RequestParam(value = "id") Long id) {
        return helpService.getById(id);
    }

    @ApiOperation("获取当前登录账号信息")
    @GetMapping("/getCurrentUserInfo")
    @ResponseBody
    public UserVo getCurrentUserInfo() {
        User user = userService.getById(StudentAuthService.getCurrentUserId());
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

    @Log(description = "更新当前登录学生信息", type = "学生端", isStaff = false)
    @ApiOperation("更新当前登录学生信息")
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public JsonResponse updateUserInfo(@Valid @RequestBody UserVo dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);

        User user = userService.getById(StudentAuthService.getCurrentUserId());
        if (StringUtils.isNotBlank(dto.getName())) {
            user.setName(dto.getName());
        }
        if (userService.updateById(user)) {
            return JsonResponseUtil.success("已修改");
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("修改密码")
    @PostMapping("/changePw")
    @Log(description = "修改密码", type = "学生端", isStaff = false)
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
        updateDto.setId(StudentAuthService.getCurrentUserId());
        updateDto.setPassword(newpw);
        updateDto.setPasswordEncode(SecurityUtils.passwordEncode(newpw));

        if (userService.changPassword(updateDto)) {
            return JsonResponseUtil.success("已修改");
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("检查当前登录用户是否绑定过微信，用于需要openid的业务常见，比如支付")
    @PostMapping("/checkWxBinding")
    @ResponseBody
    public Object checkWxBinding() {
        Long uid = StudentAuthService.getCurrentUserId();
        String openid = userMapper.getWxOpenid(uid);
        return StringUtils.isNotBlank(openid);
    }

    @ApiOperation("字典列表")
    @GetMapping("/dictList")
    public Object advertisementList(@RequestParam(value = "code") String code) {
        return dictService.listItemByCode(code);
    }
}
