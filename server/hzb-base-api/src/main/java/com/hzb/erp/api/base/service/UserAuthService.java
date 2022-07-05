package com.hzb.erp.api.base.service;

import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.pojo.StaffVO;
import com.hzb.erp.common.service.StaffService;
import com.hzb.erp.api.pc.student.service.StudentService;
import com.hzb.erp.common.service.UserService;
import com.hzb.erp.security.Util.UserAuthUtil;
import com.hzb.erp.utils.IpUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Ryan
 * description 员工登录验证服务
 **/
@Service
public class UserAuthService extends UserAuthUtil {

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    private static StaffService stStaffService;
    private static UserService stUserService;
    private static StudentService stStudentService;

    @PostConstruct
    public void init() {
        stStaffService = staffService;
        stUserService = userService;
        stStudentService = studentService;
    }

    /**
     * 从数据库获取登录者信息
     */
    public static StaffVO getLoginStaff() {
        Staff staff = stStaffService.getById(getCurrentUserId());
        StaffVO vo = new StaffVO();
        BeanUtils.copyProperties(staff, vo);
        return vo;
    }

    /**
     * 从token里获取当前登录者信息
     */
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
