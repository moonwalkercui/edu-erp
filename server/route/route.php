<?php
Route::post('/uploadimg', 'api/Service/uploadImg')->allowCrossDomain();
Route::get('/cronJobs', 'backend/index/cronJobs');
//Route::get('/loadCity.json', 'frontend/index/loadCity');
//
//Route::get('wx', 'api/wx/auth')->allowCrossDomain();
//Route::get('wxaccess', 'api/wx/access')->allowCrossDomain();
//Route::get('wxlogin', 'api/wx/login')->allowCrossDomain();

//Route::get('/', 'index/index/index');

//Route::group(['ext' => 'html'], function () {
//
//    Route::get('/', 'frontend/index/index');
//    Route::get('/index', 'frontend/index/index');
//    Route::get('/term', 'frontend/index/term');
//
//    Route::any('/login', 'frontend/login/login');
//    Route::get('/logout', 'frontend/login/logout');
//    Route::any('/register', 'frontend/login/register');
//    Route::any('/findpw', 'frontend/login/findPw');
//    Route::get('/checkmob', 'frontend/login/checkMob');
//
//    Route::get('/message', 'frontend/message/index');
//    Route::get('/message/:id', 'frontend/message/info');
//    Route::get('/delmessage/:id', 'frontend/message/delMessage');
//    Route::any('/sendmessage/:id', 'frontend/message/send');
//
//    Route::get('/me', 'frontend/me/index');
//
//})->pattern(['id' => '\d+', 'sec_id' => '\d+', 'course_id' => '\d+']);
//
Route::get('s', 'backend/login/loginform');
Route::post('/sendsmscode', 'api/Service/sendSmsCode')->allowCrossDomain();

//Route::post('/uploadimg', 'api/Service/uploadImg');
//Route::get('/loadCity.json', 'frontend/index/loadCity');
//
Route::any('wx', 'api/wx/entry')->allowCrossDomain(); // 微信服务器配置验证用
Route::any('wxJsSdkConfig', 'api/wx/jsSdkConfig')->allowCrossDomain(); // 微信服务器配置验证用
Route::get('wxauth', 'api/wx/auth')->allowCrossDomain(); // 授权登录页 可以待上state参数 如签到：/wxauth.html?state=sign
Route::get('wxaccess', 'api/wx/access')->allowCrossDomain(); // 授权授权跳转地址
//Route::get('wxlogin', 'api/wx/login')->allowCrossDomain();
//Route::group('wx', function () {
//    Route::rule('createmenu', 'wx/createMenu');
//})->prefix('api/')
//    ->header('Access-Control-Allow-Headers', 'Content-Type,usertoken')
//    ->allowCrossDomain();

// 学生中心
Route::group('/student', function () {
    Route::get('home', 'student/index/home');
    Route::get('taskList', 'student/zone/taskList');
    Route::get('clazzNotice', 'student/index/clazzNotice');
    Route::get('clazzNoticeDetail', 'student/index/clazzNoticeDetail');
    Route::get('clazzSign', 'student/course/clazzSign');
    Route::get('clazzSignInfo', 'student/course/clazzSignInfo');
    Route::post('signDo', 'student/course/signDo');
    Route::get('clazzList', 'student/index/clazzList');
    Route::get('zoneList', 'student/zone/zoneList');
    Route::get('zoneLike', 'student/zone/zoneLike');
    Route::get('calendar', 'student/course/calendar');
    Route::get('coursebyweek', 'student/course/courseByWeek');
    Route::get('coursehistory', 'student/course/history');
    Route::get('coursedetail', 'student/course/detail');
    Route::get('section', 'student/course/section');
    Route::any('scanlogin', 'student/index/scanLogin');
    Route::any('pccli', 'student/index/pcCli');
    Route::post('zoneComment', 'student/zone/zoneComment');
    Route::post('zoneCommentDel', 'student/zone/zoneCommentDel');
    Route::post('bind', 'student/index/bind');
    Route::get('studentinfo', 'student/index/studentInfo');
    Route::post('zonePublish', 'student/zone/zonePublish');

    Route::get('mediaList', 'student/media/getList');
    Route::get('mediaLike', 'student/media/mediaLike');

})->allowCrossDomain();

// 老师办公中心
Route::group('/staff', function () {
    Route::any('login', 'staff/login/loginForm');
    Route::get('home', 'staff/index/index');
    Route::get('offdutyTypes', 'staff/attendance/offdutyTypes');
    Route::post('offdutyApply', 'staff/attendance/offdutyApply');
    Route::get('offdutyHistory', 'staff/attendance/offdutyHistory');
    Route::get('signList', 'staff/attendance/signList');
    Route::get('signDo', 'staff/attendance/signDo');
    Route::get('clazzList', 'staff/clazz/getList');
    Route::get('clazzList2', 'staff/clazz/getList2');
    Route::post('publishTask', 'staff/clazz/publishTask');
    Route::get('zoneList', 'staff/zone/zoneList');
    Route::get('taskList', 'staff/zone/taskList');
    Route::post('zoneCheck', 'staff/zone/zoneCheck');
    Route::get('calendar', 'staff/index/calendar');
    Route::get('payoutTypes', 'staff/index/payoutTypes');
    Route::post('payoutApply', 'staff/index/payoutApply');
    Route::get('payoutHistory', 'staff/index/payoutHistory');

})->allowCrossDomain();


// 官网导航
Route::get('/','home/index/index');
Route::get('/3g/','home/index/index');
Route::get('/about','home/index/about');
Route::get('/students','home/index/student');
Route::get('/studentinfo/:id','home/index/studentInfo');
Route::get('/news/:nav_id?','home/index/news');
Route::get('/newsinfo/:id','home/index/newsInfo');
Route::get('/article/:id','home/index/article');
Route::get('/teacher/:id','home/index/teacher');
Route::get('/course','home/index/course');
Route::get('/course/:cate_id','home/index/course');
//Route::post('/wxconfig','home/index/wxConfig');

Route::get('/audios/[:cate_id]','home/index/audios');
Route::get('/audio/:id','home/index/audio');
Route::get('/audioplay/:id','home/index/audioplay');


