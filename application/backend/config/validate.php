<?php
return [
//    'extension_create' => [
//        'merchant_id' => 'require',
//        'extension' => 'require',
//        'money' => 'require',
//    ],
//    'extension_create_msg' => [
//        'merchant_id.require' => '{%parent_id_required}',
//        'money.require' => '缺少金额',
//    ],
    'only_name' => [
        'name|名称' => 'require',
    ],
    'staff_add' => [
        'password|密码' => 'require',
        'name|姓名' => 'require|unique:staff',
    ],
    'staff_edit' => [
        'name|姓名' => 'require|unique:staff',
    ],

    'user_saving' => [
        'name|姓名' => 'require',
        'mobile|手机号' => 'require',
    ],
    'course_saving' => [
        'date|日期' => 'require',
//        'grade_id|年级' => 'require',
        'clazz_id|班级' => 'require',
        'section_id|课时' => 'require',
        'times_id|课次' => 'require',
        'staff_id|授课老师' => 'require',
    ],
    'course_schedule_saving' => [
        'clazz_id|班级' => 'require',
        'section_id|课时' => 'require',
        'times_id|课次' => 'require',
        'staff_id|授课老师' => 'require',
        'loop_interval|频率' => 'require',
    ],
    'student_saving' => [
        'name|姓名' => 'require',
        'mobile|手机号' => 'require',
//        'grade_id|年级' => 'require',
        'clazz_id|班级' => 'require',
    ],
    'moment_saving' => [
        'images|图片' => 'require',
        'clazz_id|班级' => 'require',
    ],
    'moment_saving1' => [
        'video_url|视频' => 'require',
        'clazz_id|班级' => 'require',
    ],
    'clazz_saving' => [
        'name|名称' => 'require',
//        'grade_id|年级' => 'require',
    ],
    'zone_task_saving' => [
        'content|内容' => 'require',
        'clazz_id|班级' => 'require',
    ],
    'goods_saving' => [
        'name|商品名称' => 'require',
        'image|商品图片' => 'require',
    ],
    'notification_saving' => [
        'title|标题' => 'require',
        'content|内容' => 'require',
    ],
    'adv_saving' => [
        'title|标题' => 'require',
        'image|图片' => 'require',
    ],
    'vod_saving' => [
        'title|标题' => 'require',
        'file_id|文件ID' => 'require',
        'cover_url|封面' => 'require',
        'video_url|视频' => 'require',
    ],
    'auth_role_add' => [
        'name|名称' => 'require',
    ],

    'coupon_saving' => [
        'amount|满额' => 'require',
        'off|减额' => 'require',
    ],
    'exam_category_saving' => [
        'name|名称' => 'require',
    ],

    'exam_paper_saving' => [
        'title|标题' => 'require',
        'total_score|总分' => 'require',
        'start_time|开始时间' => 'require',
        'end_time|结束时间' => 'require',
        'cover|封面' => 'require',
    ],
    'exam_question_saving' => [
        'content|试题内容' => 'require',
        'type|试题类型' => 'require',
        'right_answer|试题答案' => 'require',
    ],
    'exam_question_option_saving' => [
        'content|选项内容' => 'require',
        'question_id' => 'require',
    ],
    'exam_question_body_saving' => [
        'content|内容' => 'require',
        'title|标题' => 'require',
    ],
//    'course_saving' => [
//        'title|课程名' => 'require',
//        'category_id|课程分类' => 'require',
//        'cover|课程封面' => 'require',
//        'price|价格' => 'require',
//        'teacher_id|讲师' => 'require',
//    ],
    'course_category_saving' => [
        'name|名称' => 'require',
    ],
    'course_section_saving' => [
        'title|课时名' => 'require',
        'course_id' => 'require',
    ],
    'course_section_vid' => [
        'video_id|视频' => 'require',
    ],
    'save_live' => [
        'room_id|直播房间' => 'require',
    ],
    'course_section_eid' => [
        'exam_paper_id|试卷' => 'require',
    ],
    'user_level_save' => [
        'name|等级名称' => 'require',
        'need_points|所需积分' => 'require',
    ],
    'article_saving' => [
        'title|文章标题' => 'require',
        'content|文章内容' => 'require',
    ],
    'article_category_saving' => [
        'name|文章分类标题' => 'require',
    ],
    'advertisement_saving' => [
        'title|广告标题' => 'require',
        'image|广告图片' => 'require',
    ],
    'message_sending' => [
        'title|消息标题' => 'require',
        'content|消息内容' => 'require',
        'type|消息类型' => 'require',
        'to_users|有效手机号' => 'requireIf:type,1',
    ],
    'msg_template_saving' => [
        'title|标题' => 'require',
        'content|内容' => 'require',
    ],
    'nav_saving' => [
        'name|名称' => 'require',
        'position|位置' => 'require',
    ],
    'download_saving' => [
        'title|文件标题' => 'require',
        'att_id|选择文件' => 'require',
    ],
    'save_knowledge' => [
        'title|标题' => 'require',
    ],
    'save_h5' => [
        'title|标题' => 'require',
        'content|内容' => 'require',
    ],
    'save_test' => [
        'title|标题' => 'require',
        'category_id|分类' => 'require',
    ],
    'schedule_save_plan' => [
        'name|计划名称' => 'require',
        'start_am|上午开始时间' => 'require|gt:0',
        'end_am|上午结束时间' => 'require|lt:start_pm|gt:start_am',
        'start_pm|下午开始时间' => 'require|gt:end_am',
        'end_pm|下午结束时间' => 'require|gt:start_pm',
        'weekdays|每周工作日' => 'require',
//        'duration|每时间段时长(分钟)' => 'require|number|gt:5',
//        'service_count|每时间段顾客数' => 'require|number|gt:0',
    ],
    'schedule_save_plan2' => [
        'name|计划名称' => 'require',
        'weekdays|每周工作日' => 'require',
        'times|自定义开始时间点' => 'require',
//        'duration|每时间段时长(分钟)' => 'require|number|gt:5',
//        'service_count|每时间段顾客数' => 'require|number|gt:0',
    ],
    'schedule_save_plan3' => [
        'name|计划名称' => 'require',
        'weekdays|每周工作日' => 'require',
    ],
    'schedule_save_project' => [
        'name|项目名称' => 'require',
//        'category_id|分类' => 'require',
    ],
    'schedule_save_staff' => [
        'staff_id|分类' => 'require',
    ],
    'save_wx_menu' => [
        'name|菜单名称' => 'require',
//        'parent_id|上级菜单' => 'require',
//        'type|行为' => 'require',
//        'value|内容' => 'require',
    ],
    'staff_save' => [
        'name|姓名' => 'require',
        'password|密码' => 'require',
    ],

    'save_offduty' => [
        'type_id|请假类型' => 'require',
        'reason|请假原因' => 'require',
        'start_date|开始日期' => 'require',
        'end_date|结束日期' => 'require',
    ],

    'save_payout' => [
        'money|金额' => 'require',
        'type_id|请假类型' => 'require',
        'reason|请假原因' => 'require',
    ],
];