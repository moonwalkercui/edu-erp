<?php
// +----------------------------------------------------------------------
// | 生成静态页的规则文件 详细见readme
// | @author  冷风崔
// +----------------------------------------------------------------------

return [
    '@index' => 'index/index', // 这个是首页 会生成在dist目录下
    '@news' => 'news/index',
    'news_:id' => ['news/find', 'article'],
//    'job_:id' => ['jobs/find', 'func:getjobids'],
//    'job_:id' => ['index/index', 'func:dist/index/test'],
];
