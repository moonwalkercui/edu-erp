package com.hzb.erp.service;

import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.pojo.vo.StaffVO;
import com.hzb.erp.common.service.StaffOrginfoService;
import com.hzb.erp.common.service.StaffService;
import com.hzb.erp.common.service.StudentService;
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
 * description 通用服务
 **/
@Service
public class UserAuthService extends UserAuthUtil {

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StaffOrginfoService staffOrginfoService;

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
     * 从数据库获取登录者信息
     */
    public static Student getDefaultStudentByUserId(Long userId) {
        return stStudentService.getDefaultStudent(userId);
    }

//    /**
//     * 从数据库获取登录者信息
//     */
//    public static StudentLoginVo getLoginStudent() {
//        User user = stUserService.getById(getCurrentUserId());
//        StudentLoginVo vo = new StudentLoginVo();
//        BeanUtils.copyProperties(user, vo);
//        return vo;
//    }

//
//
//    /**
//     * 获取当前员工信息
//     */
//    public static Staff getCurrentStaff() {
//        JwtUserDetails userDetails = getUserInContext();
//        return stStaffService.getById(userDetails.getId());
//    }
//
//    /**
//     * 获取当前登录的学生账号
//     */
//    public static Student getCurrentStudent() {
//        JwtUserDetails userDetails = getUserInContext();
//        return stStudentService.getById(userDetails.getId());
//    }

    /**
    * 记录登录信息
    * */
    public static void updateUserLoginInfo(HttpServletRequest request, Long userId) {
        User user = stUserService.getById(userId);
        user.setLatestLoginIp(IpUtil.getIpAddr(request));
        user.setLatestLoginTime(LocalDateTime.now());
        user.setLoginTimes(user.getLoginTimes() + 1);
        stUserService.updateById(user);
    }
}
