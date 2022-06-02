package com.hzb.erp.studentMobile.service;

import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.common.service.UserService;
import com.hzb.erp.security.Util.UserAuthUtil;
import com.hzb.erp.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Ryan
 * description 学生登录认证服务
 **/
@Service
public class StudentAuthService extends UserAuthUtil {
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    private static UserService stUserService;
    private static StudentService stStudentService;

    @PostConstruct
    public void init() {
        stUserService = userService;
        stStudentService = studentService;
    }

    /**
     * 从token里获取当前登录者信息
     */
    @Nullable
    public static Student getCurrentStudent() {
        Long userId = getCurrentUserId();
        return stStudentService.getDefaultStudent(userId);
    }

    /**
     * 记录登录信息
     */
    public static void updateUserLoginInfo(HttpServletRequest request, Long userId) {
        User user = stUserService.getById(userId);
        user.setLatestLoginIp(IpUtil.getIpAddr(request));
        user.setLatestLoginTime(LocalDateTime.now());
        user.setLoginTimes(user.getLoginTimes() + 1);
        stUserService.updateById(user);
    }
}
