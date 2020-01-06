<?php
$api = app('Dingo\Api\Routing\Router');
// cors 为跨域中间件 api.throttle为api截流中间件
$api->version('v1', ['namespace' => 'App\Api\V1\Controllers','middleware'=>['cors' , 'api.throttle' ] ,'limit' => 300, 'expires' => 5], function ($api) {
    // 验证码
    $api->get('captcha/{code}', 'OpenApi@captcha');

//    // 表单token
//    $api->get('csrftoken', function () {
//        var_dump(csrf_token());
//    });
    $api->group(['prefix' => 'auth'], function ($api) {
        $api->post('login', 'user\AuthApi@login')->name('auth.login');
        $api->post('register', 'user\AuthApi@register')->name('auth.register');
        $api->get('user', 'user\AuthApi@getUser'); // 获取用户信息
        $api->get('logout', 'user\AuthApi@logout');
        $api->get('wxlogin', 'user\AuthApi@wxLogin');
        $api->post('wxusernamelogin', 'user\AuthApi@wxUsernameLogin');
        $api->post('wxusernameregister', 'user\AuthApi@wxUsernameRegister');
//        $api->get('wxdecryptlogin', 'user\AuthApi@wxDecryptLogin');
//        $api->get('wxupdateuserinfo', 'user\AuthApi@wxUpdateUserInfo');
//        $api->get('refresh_token', 'user\AuthApi@refreshToken'); // 刷新token
    });

    $api->group(['prefix' => 'normal','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('systemnumber', 'user\NormalApi@systemNumber');
        $api->get('articles', 'user\NormalApi@articles');
        $api->get('advertisements', 'user\NormalApi@advertisements');
        $api->post('advertisementsave', 'user\NormalApi@advertisementSave');
        $api->post('advertisementdel', 'user\NormalApi@advertisementDel');
        $api->get('notifications', 'user\NormalApi@notifications');
        $api->post('notificationsave', 'user\NormalApi@notificationSave');
        $api->post('notificationsdel', 'user\NormalApi@notificationDel');
        $api->get('zone', 'user\NormalApi@zone');
        $api->post('zonesave', 'user\NormalApi@zoneSave');
        $api->post('zonedel', 'user\NormalApi@zoneDel');
        $api->get('regions', 'user\NormalApi@regions');
        $api->get('helper', 'user\NormalApi@helper');
        $api->get('checknotice', 'user\NormalApi@checkNotice');

//        $api->post('createorg', 'user\NormalApi@createOrg'); // 不验证会员的组织状态
        $api->get('findorg', 'user\NormalApi@findOrg');
//        $api->get('cancreateorg', 'user\NormalApi@canCreateOrg');
//        $api->get('canjoinorg', 'user\NormalApi@canJoinOrg');
//        $api->get('orgapply', 'user\NormalApi@orgApply'); // 通过组织编码申請加入组织
//        $api->get('getappliedorg', 'user\NormalApi@getAppliedOrg');
        $api->get('mreqtoken', 'user\NormalApi@makeMultiReqToken'); //
//        $api->get('getuserorgs', 'user\UserApi@getOrgList');
//        $api->post('switchover', 'user\UserApi@switchover');

    });
    $api->group(['prefix' => 'user','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {

        $api->get('table', 'user\UserApi@getAll');
        $api->get('profile', 'user\UserApi@getProfile');
        $api->post('edit', 'user\UserApi@edit');
        $api->post('editme', 'user\UserApi@editMe');
        $api->post('create', 'user\UserApi@create');
        $api->post('changepw', 'user\UserApi@changePw');
//        $api->post('setDepartment', 'user\UserApi@setDepartment');
//        $api->get('find', 'user\UserApi@getOne');
    });
    $api->group(['prefix' => 'voucher','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\VoucherApi@getAll');
        $api->post('save', 'user\VoucherApi@save');
        $api->post('delete', 'user\VoucherApi@delete');
        $api->get('log', 'user\VoucherApi@log');
    });
//    $api->group(['prefix' => 'join','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
//        $api->get('applications', 'user\JoinApi@applicationList'); // 列表
//        $api->post('approve', 'user\JoinApi@handleApprove');
//        $api->post('reject', 'user\JoinApi@handleReject');
////        $api->post('invite', 'user\JoinApi@inviteUser');
////        $api->post('reject', 'user\JoinApi@inviteReject');
////        $api->post('inviteCancel', 'user\JoinApi@inviteCancel');
////        $api->get('invited', 'user\JoinApi@invitedList');
////        $api->get('myInvitation', 'user\JoinApi@myInvitation');
////        $api->get('myApplication', 'user\JoinApi@myApplication');
//    });
    // 客户管理
    $api->group(['prefix' => 'member','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\MemberApi@getAll');
//        $api->get('getbyclass', 'user\MemberApi@getByClass');
        $api->get('find', 'user\MemberApi@getOne');
        $api->get('ranking', 'user\MemberApi@ranking');
//        $api->get('mine', 'user\MemberApi@myMembers');
        $api->post('create', 'user\MemberApi@create');
        $api->post('edit', 'user\MemberApi@edit');
        $api->post('editcolumn', 'user\MemberApi@editColumn');
        $api->post('delete', 'user\MemberApi@delete');
        $api->post('productquantityedit', 'user\MemberApi@productQuantityEdit');
        $api->post('productquantityadd', 'user\MemberApi@productQuantityAdd');
//        $api->post('changeowner', 'user\MemberApi@changeOwner');
        $api->post('changesalesman', 'user\MemberApi@changeSalesman');

    });
    // 消息管理
    $api->group(['prefix' => 'message','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('mine', 'user\MessageApi@getMine');
        $api->post('send', 'user\MessageApi@send');
        $api->get('read', 'user\MessageApi@read');
        $api->post('delete', 'user\MessageApi@delete');
    });
    $api->group(['prefix' => 'shop','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('goods', 'user\ShopApi@goods');
        $api->get('findgoods', 'user\ShopApi@findGoods');
        $api->post('savegoods', 'user\ShopApi@saveGoods');
        $api->get('category', 'user\ShopApi@category');
        $api->post('savecategory', 'user\ShopApi@saveCategory');
        $api->post('delcategory', 'user\ShopApi@delCategory');
        $api->post('delgoods', 'user\ShopApi@delGoods');
    });

    $api->group(['prefix' => 'org','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
//        $api->post('edit', 'user\OrganizationApi@edit');
//        $api->get('close', 'user\OrganizationApi@deleteOrganization');
//        $api->get('myorg', 'user\OrganizationApi@myOrganization');
//        $api->get('quit', 'user\OrganizationApi@quitOrganization');
        $api->get('divisions', 'user\OrganizationApi@divisions');
        $api->get('divisionfind', 'user\OrganizationApi@divisionFind');
        $api->post('divisioncreate', 'user\OrganizationApi@divisionCreate');
        $api->post('divisionedit', 'user\OrganizationApi@divisionEdit');
        $api->post('divisioncoordinate', 'user\OrganizationApi@divisionEditCoordinate');
//        $api->post('divisionsetdefault', 'user\OrganizationApi@divisionSetDefault');
        $api->post('divisiondelete', 'user\OrganizationApi@divisionDelete');
    });
    $api->group(['prefix' => 'setting','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->post('savesetting', 'user\SettingApi@saveSetting');
        $api->get('getsetting', 'user\SettingApi@getSetting');
        $api->get('getlogs', 'user\SettingApi@getLogs');
        $api->get('getlogtype', 'user\SettingApi@getLogType');
        $api->get('wxmsgtemplate', 'user\SettingApi@wxMsgTemplate');
        $api->post('savewxmsgtemplate', 'user\SettingApi@saveWxMsgTemplate');
    });
    $api->group(['prefix' => 'depart','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\DepartmentApi@getAll');
        $api->get('tablebyinternal', 'user\DepartmentApi@getAllByInternal');
        $api->get('tablewithchild', 'user\DepartmentApi@getAllWithChild');
        $api->get('tablewithdivision', 'user\DepartmentApi@getAllWithDivision');
        $api->post('create', 'user\DepartmentApi@create');
        $api->post('edit', 'user\DepartmentApi@edit');
        $api->post('delete', 'user\DepartmentApi@delete');
        $api->post('saveUsers', 'user\DepartmentApi@saveUsers');
    });
    // 商品管理
    $api->group(['prefix' => 'product','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\ProductApi@getAll');
//        $api->get('tablebycate', 'user\ProductApi@getByCategory');
        $api->get('find', 'user\ProductApi@getOne');
        $api->post('create', 'user\ProductApi@create');
        $api->post('edit', 'user\ProductApi@edit');
        $api->post('delete', 'user\ProductApi@delete');
        $api->get('stop', 'user\ProductApi@stop');
        $api->get('open', 'user\ProductApi@open');
        $api->get('members', 'user\ProductApi@members');
        $api->post('editgoupbuy', 'user\ProductApi@editGoupbuy');
    });
//    $api->group(['prefix' => 'badge','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
//        $api->get('table', 'user\ProductApi@badges');
//        $api->post('save', 'user\ProductApi@saveBadges');
//        $api->post('delete', 'user\ProductApi@deleteBadges');
//    });
    // 财务管理
    $api->group(['prefix' => 'proceeds','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\ProceedsApi@getAll');
        $api->post('confirm', 'user\ProceedsApi@handleConfirm');
        $api->post('receive', 'user\ProceedsApi@receive');
//        $api->get('items', 'user\ProceedsApi@getItems');
//        $api->get('mine', 'user\ProceedsApi@mine');
//        $api->post('create', 'user\ProceedsApi@create');
//        $api->post('verify', 'user\ProceedsApi@verify');
//        $api->post('createitem', 'user\ProceedsApi@createItem');
//        $api->post('edititem', 'user\ProceedsApi@editItem');
//        $api->post('deleteitem', 'user\ProceedsApi@deleteItem');
    });
    // 课程管理
    $api->group(['prefix' => 'course','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\CourseApi@getAll');
        $api->get('mine', 'user\CourseApi@getMine');
        $api->get('tablebyday', 'user\CourseApi@getAllByDay');
        $api->get('minebyday', 'user\CourseApi@getMineByDay');
        $api->get('mycalendardate', 'user\CourseApi@getMyCalendarDate');
        $api->post('create', 'user\CourseApi@create');
        $api->get('getweekschedule', 'user\CourseApi@getWeekSchedule');
        $api->get('generatecoursebysch', 'user\CourseApi@generateCourseBySch');
        $api->post('delweeksch', 'user\CourseApi@delWeekSch');
        $api->post('createbyweek', 'user\CourseApi@createByWeek');
        $api->post('delete', 'user\CourseApi@delete');
        $api->post('edittitle', 'user\CourseApi@editTitle');
        $api->get('stop', 'user\CourseApi@stop');
        $api->get('open', 'user\CourseApi@open');
        $api->get('find', 'user\CourseApi@getOne');
        $api->post('edit', 'user\CourseApi@edit');
        $api->get('refreshclash', 'user\CourseApi@refreshClash');
        $api->get('homework', 'user\CourseApi@homework');
        $api->post('homeworkcreate', 'user\CourseApi@homeworkCreate');
//        $api->post('refreshMember', 'user\CourseApi@refreshMember');
//        $api->get('mine', 'user\CourseApi@myCourses');
    });
    // 课程签到管理
    $api->group(['prefix' => 'coursesign','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\CourseSignApi@getAll');
        $api->get('getwithdeal', 'user\CourseSignApi@getWithDeal');
        $api->get('allograph', 'user\CourseSignApi@allograph');
        $api->post('delete', 'user\CourseSignApi@delete');
        $api->post('givepoints', 'user\CourseSignApi@givePoints');
    });
    // 班级管理
//    $api->group(['prefix' => 'class','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
//        $api->get('table', 'user\ClassesApi@getAll');
//        $api->post('create', 'user\ClassesApi@create');
//        $api->post('edit', 'user\ClassesApi@edit');
//        $api->post('delete', 'user\ClassesApi@delete');
//        $api->post('savemembers', 'user\ClassesApi@saveMembers');
//    });
    // 物料管理
//    $api->group(['prefix' => 'material','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
//        $api->get('table', 'user\MaterialApi@getAll');
//        $api->get('log', 'user\MaterialApi@log');
//        $api->post('create', 'user\MaterialApi@create');
//        $api->post('edit', 'user\MaterialApi@edit');
//        $api->post('apply', 'user\MaterialApi@apply');
//        $api->post('delete', 'user\MaterialApi@delete');
//        $api->post('changequantity', 'user\MaterialApi@changeQuantity');
//        $api->post('approve', 'user\MaterialApi@handleApprove');
//        $api->post('reject', 'user\MaterialApi@handleReject');
//    });
    // Gift管理
    $api->group(['prefix' => 'gift','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\GiftApi@getAll');
        $api->post('save', 'user\GiftApi@save');
        $api->post('delete', 'user\GiftApi@delete');
        $api->get('category', 'user\GiftApi@categories');
        $api->post('saveCategory', 'user\GiftApi@saveCategory');
        $api->get('orders', 'user\GiftApi@orders');
        $api->post('approve', 'user\GiftApi@approve');
        $api->post('reject', 'user\GiftApi@reject');
    });
    // 教室管理
    $api->group(['prefix' => 'room','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\RoomApi@getAll');
        $api->post('create', 'user\RoomApi@create');
        $api->post('edit', 'user\RoomApi@edit');
        $api->post('delete', 'user\RoomApi@delete');
    });
    // 商品分类管理
    $api->group(['prefix' => 'category','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\CategoryApi@getAll');
        $api->get('find', 'user\CategoryApi@getOne');
        $api->get('properties', 'user\CategoryApi@getProperties');
        $api->post('create', 'user\CategoryApi@create');
        $api->post('edit', 'user\CategoryApi@edit');
        $api->post('delete', 'user\CategoryApi@delete');
    });
    // 商品分类类型管理
    $api->group(['prefix' => 'type','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\CategoryTypeApi@getAll');
        $api->get('find', 'user\CategoryTypeApi@getOne');
        $api->post('create', 'user\CategoryTypeApi@create');
        $api->post('edit', 'user\CategoryTypeApi@edit');
        $api->post('delete', 'user\CategoryTypeApi@delete');
        $api->post('createproperty', 'user\CategoryTypeApi@createProperty');
        $api->post('editproperty', 'user\CategoryTypeApi@editProperty');
        $api->post('deleteproperty', 'user\CategoryTypeApi@deleteProperty');
    });
    // 订单管理
    $api->group(['prefix' => 'order','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('table', 'user\OrderApi@getAll');
        $api->get('find', 'user\OrderApi@getOne');
        $api->get('salesman', 'user\OrderApi@salesman');
        $api->get('listbysalesman', 'user\OrderApi@listBySalesman');
//        $api->get('mine', 'user\OrderApi@getMine');
//        $api->post('create', 'user\OrderApi@create');
//        $api->post('edit', 'user\OrderApi@edit');
//        $api->post('delete', 'user\OrderApi@delete');
//        $api->post('approve', 'user\OrderApi@handleApprove');
//        $api->post('reject', 'user\OrderApi@handleReject');
        $api->get('refunds', 'user\OrderApi@refunds');
        $api->get('refunds', 'user\OrderApi@refunds');
        $api->post('refund', 'user\OrderApi@refund');
    });
    // 权限
    $api->group(['prefix' => 'rbac','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('orgroles', 'user\RbacApi@orgRoles');
        $api->get('findrole', 'user\RbacApi@findRole');
        $api->post('roleedit', 'user\RbacApi@roleEdit');
        $api->post('rolecreate', 'user\RbacApi@roleCreate');
        $api->post('roledelete', 'user\RbacApi@roleDelete');
        $api->get('finduserroles', 'user\RbacApi@findUserRoles');
        $api->post('edituserroles', 'user\RbacApi@editUserRoles');
        $api->get('permissions', 'user\RbacApi@permissions');
    });
    // 统计
    $api->group(['prefix' => 'statistics','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('total', 'user\StatisticsApi@total');
        $api->get('orderday', 'user\StatisticsApi@orderDay');
        $api->get('orderdaycount', 'user\StatisticsApi@orderDayCount');
        $api->get('ordermonth', 'user\StatisticsApi@orderMonth');
        $api->get('proceedsday', 'user\StatisticsApi@proceedsDay');
        $api->get('proceedsmonth', 'user\StatisticsApi@proceedsMonth');
        $api->get('signday', 'user\StatisticsApi@signDay');
        $api->get('courseday', 'user\StatisticsApi@courseDay');
        $api->get('coursemonth', 'user\StatisticsApi@courseMonth');
        $api->get('usercourses', 'user\StatisticsApi@userCourses');
        $api->get('productorders', 'user\StatisticsApi@productOrders');
        $api->get('userordersmoney', 'user\StatisticsApi@userOrdersMoney');
        $api->get('userorderscount', 'user\StatisticsApi@userOrdersCount');
        $api->get('membersigns', 'user\StatisticsApi@memberSigns');
    });
    // 统计
    $api->group(['prefix' => 'share','middleware'=>['auth:api' , 'userPermission' , 'userStatus']], function ($api) {
        $api->get('redpacketlog', 'user\ShareApi@redPacketLog');
        $api->post('redpacketsetting', 'user\ShareApi@redPacketSetting');
    });
    // 系统接口
    $api->group(['prefix' => 'sys','middleware'=>['auth:api' , 'userStatus']], function ($api) {
        $api->get('leftmenu', 'user\SystemApi@getLeftMenu');
        $api->any('uploader', 'user\SystemApi@uploader');
        $api->post('sentemail', 'user\SystemApi@sentEmail');
        $api->post('advice', 'user\AdviceApi@create');
        $api->get('qrcode', 'user\SystemApi@getQrcode');
        $api->post('valueupdate', 'user\SystemApi@valueUpdate');
        $api->any('import', 'user\SystemApi@importExcel');
    });
    // 开放shuju
    $api->group(['prefix' => 'open'], function ($api) {
        $api->get('getstatus', 'OpenApi@getStatus'); // 获取配置状态
        $api->get('regions', 'OpenApi@regions');
//        $api->get('getcities', 'OpenApi@getCities');
//        $api->get('checkorgcodeexist', 'OpenApi@checkOrgcodeExist');
    });
    // 聊天
//    $api->group(['prefix' => 'chat','middleware'=>['auth:api']], function ($api) {
//        $api->post('bind', 'ChatApi@bind');
//        $api->post('say', 'ChatApi@say');
//        $api->get('history', 'ChatApi@getHistoryList');
//        $api->get('onlineusers', 'ChatApi@getOnlineUsers');
//        $api->get('fetchmessage', 'ChatApi@fetchMessage');
//        $api->post('collect', 'ChatApi@collect');
//        $api->post('collectdel', 'ChatApi@collectDel');
//        $api->get('getcollection', 'ChatApi@getCollection');
//        $api->get('historymsg', 'ChatApi@historyMessage');
//    });

    // member登陆等
    $api->group(['prefix' => 'mauth'], function ($api) {
        $api->get('login', 'member\AuthApi@login');
//        $api->get('updateuserinfo', 'member\AuthApi@updateUserInfo');
        $api->get('getusermobile', 'member\AuthApi@getUserMobile');

//        $api->get('createpool', 'member\AuthApi@createPool');
//        $api->post('parentbindmember', 'member\AuthApi@parentBindMember');
    });
    $api->group(['prefix' => 'mpage'], function ($api) {
//        $api->get('courses', 'member\Page@courses');
        $api->get('homedata', 'member\Page@homeData');
        $api->get('gifts', 'member\Page@giftList');
        $api->get('giftcategories', 'member\Page@giftCategories');
        $api->get('goods', 'member\Page@goodsList');
        $api->get('zone', 'member\User@zoneList');
        $api->get('courselist', 'member\Course@getAll');
    });

    $api->group(['prefix' => 'mcourse','middleware'=>['auth:api_member']], function ($api) {
        $api->get('listbyweek', 'member\Course@getByWeek');
        $api->get('detail', 'member\Course@find');

        $api->get('qrcode', 'member\Course@qrcode');
        $api->get('calendardays', 'member\Course@calendarDays');
        $api->get('getbyday', 'member\Course@getByDay');
        $api->post('sign', 'member\Course@sign');
//        $api->get('days', 'member\Course@getCalendarDate');
//        $api->get('listbyday', 'member\Course@getByDay');
//        $api->get('listbyproduct', 'member\Course@getByProduct');
//        $api->get('signs', 'member\Course@signs');
//        $api->get('signing', 'member\Course@signing');
    });
    // c端相关接口
    $api->group(['prefix' => 'mproduct'], function ($api) {
        $api->get('list', 'member\Product@getAll');
        $api->get('detail', 'member\Product@detail');
        $api->get('properties', 'member\Product@propertyTree');
        $api->get('propertierel', 'member\Product@getPropertyRelation');
        $api->get('categories', 'member\Product@categories');
//        $api->get('getlongtermlog', 'member\Product@longtermLog');
//        $api->post('makeappointment', 'member\Product@makeAppointment');
    });

    $api->group(['prefix' => 'mdivision'], function ($api) {
        $api->get('tree', 'member\Division@tree');
        $api->get('list', 'member\Division@getAll');
        $api->get('detail', 'member\Division@detail');
    });
    $api->group(['prefix' => 'madv'], function ($api) {
        $api->get('find', 'member\Adv@find');
        $api->get('notifications', 'member\Adv@notifications');
    });
    $api->group(['prefix' => 'mzone','middleware'=>['auth:api_member']], function ($api) {
        $api->post('like', 'member\User@zoneLike');
    });
    $api->group(['prefix' => 'muser'], function ($api) {
        $api->get('list', 'member\User@getAll');
        $api->get('profile', 'member\User@profile');
    });
    $api->group(['prefix' => 'mteacher','middleware'=>['auth:api_member']], function ($api) {
        $api->get('login', 'member\User@login');
        $api->get('scanlog', 'member\User@scanLog');
        $api->get('signlog', 'member\User@signLog');
        $api->post('allograph', 'member\User@allograph');

        $api->post('scan', 'member\User@scan');
        $api->get('getcalendardate', 'member\User@getCalendarDate');
        $api->get('getcoursebyday', 'member\User@getCourseByDay');
        $api->get('getmembers', 'member\User@getMembers');
        $api->get('homework', 'member\User@getHomework');
        $api->post('savehomework', 'member\User@saveHomework');
        $api->post('givepoint', 'member\User@givePoint');
    });
    $api->group(['prefix' => 'mnormal','middleware'=>['auth:api_member']], function ($api) {
        $api->any('uploader', 'member\Normal@uploader');
        $api->get('homedata', 'member\Normal@homeData'); // 新版删除
        $api->post('sendsmsode', 'member\Normal@sendSmsCode');
        $api->post('bindmobile', 'member\Normal@bindMobile');
        $api->post('bindmobilebywx', 'member\Normal@bindMobileByWx');
    });
    $api->group(['prefix' => 'morder','middleware'=>['auth:api_member']], function ($api) {
//        $api->get('confirmform', 'member\Order@confirmForm');
        $api->post('courseconfirm', 'member\Order@courseConfirm');
        $api->post('productconfirm', 'member\Order@productConfirm');
        $api->post('cancelorder', 'member\Order@cancelOrder');
        $api->post('refundorder', 'member\Order@refundOrder');
        $api->post('pay', 'member\Order@payOrder');
    });

    $api->group(['prefix' => 'mshop','middleware'=>['auth:api_member']], function ($api) {

        $api->post('confirm', 'member\Shop@confirm');
        $api->get('address', 'member\Shop@getAddress');
        $api->post('saveaddress', 'member\Shop@saveAddress');
        $api->post('deladdress', 'member\Shop@delAddress');
        $api->post('makeorder', 'member\Shop@makeOrder');
        $api->get('orders', 'member\Shop@getOrders');
    });
    $api->group(['prefix' => 'mgift','middleware'=>['auth:api_member']], function ($api) {
        $api->post('exchange', 'member\Gift@exchange');
        $api->get('orders', 'member\Gift@orders');
    });
    $api->group(['prefix' => 'mcenter','middleware'=>['auth:api_member']], function ($api) {
        $api->get('index', 'member\Mcenter@index');
        $api->get('courses', 'member\Mcenter@myCoursesQuantity');
        $api->get('signs', 'member\Mcenter@mySigns');
        $api->get('orders', 'member\Mcenter@myOrders');
        $api->get('profile', 'member\Mcenter@myProfile');
        $api->post('edit', 'member\Mcenter@editProfile');
        $api->get('vouchers', 'member\Mcenter@myVouchers');
        $api->post('getvoucher', 'member\Mcenter@getVoucher');
        $api->post('usevoucher', 'member\Mcenter@useVoucher');
        $api->get('addcollection', 'member\Mcenter@collect');
        $api->get('removecollection', 'member\Mcenter@removeCollection');
        $api->post('topup', 'member\Mcenter@topUp');
//        $api->get('topupstage', 'member\Mcenter@topUpStage');
        $api->get('getvoucher', 'member\Mcenter@getVoucherByCode');
        $api->get('redpacket', 'member\Mcenter@redpacketLog');
//        $api->get('redpacketfriends', 'member\Mcenter@redPacketFriends');
        $api->post('makeredpacketbyshare', 'member\Mcenter@makeRedPacketByShare');
        $api->get('shareqr', 'member\Mcenter@shareQr');
        $api->get('drawqrposter', 'member\Mcenter@drawQrPoster');
        $api->get('collectformids', 'member\Mcenter@collectFromIds');
    });
//    $api->group(['prefix' => 'mcategory','middleware'=>['auth:api_member']], function ($api) {
//        $api->get('list', 'member\Category@getAll');
//        $api->get('child', 'member\Category@getChildAndProduct');
//    });
//    $api->group(['prefix' => 'mfavorite','middleware'=>['auth:api_member']], function ($api) {
//        $api->get('mine', 'member\Favorite@mine');
//        $api->post('add', 'member\Favorite@add');
//        $api->post('remove', 'member\Favorite@remove');
//    });
    $api->group(['prefix' => 'adv','middleware'=>['auth:api_member']], function ($api) {
        $api->get('find', 'member\Adv@find');
    });

});

Route::any('/' .API_PREFIX. '/paymentcallback', 'Payment@callback'); // 支付回调
Route::any('/' .API_PREFIX. '/refundcallback', 'Payment@refundNotice'); // 退款回调
// 定时处理红包过期处理程序
Route::any('/' .API_PREFIX. '/cronRedPacketExpire', 'Cron@handleRedPacketExpire');

//// 定义api里的异常格式 （已经在config/api.php中定义了errorFormat,muser）
//app('Dingo\ApiUser\Exception\Handler')->setErrorFormat([
//    'status' => 'error',
//    'msg' => ':message',
//    'code' => ':code',
//    'status_code' => ':status_code',
//    'debug' => ':debug'
//]);
//// 将api所有的 Exception 全部交给 App\Exceptions\Handler 来处理
//app('api.exception')->register(function (\Exception $exception) {
//    $request = Illuminate\Http\Request::capture();
//    return app('App\Exceptions\Handler')->render($request, $exception);
//});

// 聊天控制器
//Route::namespace('GatewayWorker')->group(function () {
//    Route::post('/chatBind', 'Server@bind');
//    Route::post('/chatSay', 'Server@handleSay');
//});

// 产品介绍页 miniapp调用
//Route::get('/product/{id}', 'OpenHtml\Product@description');

//Route::get('/', function () {
//    return ' COMING SOON';
//});



//Route::get('/refreshApiPermissionToDbTable', function () {
//        //    $res = app()->router->getRoutes(); // 所有的api之外的路由
//        $api_route = app('Dingo\Api\Routing\Router')->getRoutes(); // 所有api路由
//        foreach ($api_route['v1']->getRoutes() as $route){
//            $uri = strtolower($route->uri());
//            $name = ltrim($uri,'/');
//            $res = \App\Model\Permission::firstOrCreate([
//                'name' => str_replace('/','_',$name),
//                'uri' => $uri
//            ]);
//            $res->method = strtolower($route->methods[0]);
//            $res->save();
//        }
//        echo 'ok';
//});

//Route::get('resource', 'Common\Resources@getUploadFile');

//Route::any('captcha-test', function() {
//    session('okok', 111111);
//    $image = new \App\Service\Api\Captcha();    //图片长度、宽度、字符个数
//    return response($image->outImg(), 200, [
//        'Content-Type' => 'image/png',
//    ]);
//});