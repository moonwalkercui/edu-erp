<?php
$ApiPrefix = 'demo';
if(isset($_SERVER['REQUEST_URI'])) {
    $url_arr = explode('/', $_SERVER['REQUEST_URI']);
    $ApiPrefix = $url_arr[1];
}
if(!defined('API_PREFIX')) {
    define('API_PREFIX', $ApiPrefix);
}
if(!defined('API_DOMAIN')) {
    define('API_DOMAIN', 'api.xiaoyuan.net.cn');
}
if(!defined('API_DOMAIN_FULL')) {
    define('API_DOMAIN_FULL', 'https://'. API_DOMAIN);
}
if(!defined('API_DOMAIN_PREFIX')) {
    define('API_DOMAIN_PREFIX', 'https://'. API_DOMAIN . '/' .API_PREFIX);
}

/*
|--------------------------------------------------------------------------
| Create The Application
|--------------------------------------------------------------------------
|
| The first thing we will do is create a new Laravel application instance
| which serves as the "glue" for all the components of Laravel, and is
| the IoC container for the system binding all of the various parts.
|
*/

$app = new Illuminate\Foundation\Application(
    $_ENV['APP_BASE_PATH'] ?? dirname(__DIR__)
);

/*
|--------------------------------------------------------------------------
| Bind Important Interfaces
|--------------------------------------------------------------------------
|
| Next, we need to bind some important interfaces into the container so
| we will be able to resolve them when needed. The kernels serve the
| incoming requests to this application from both the web and CLI.
|
*/

$app->singleton(
    Illuminate\Contracts\Http\Kernel::class,
    App\Http\Kernel::class
);

$app->singleton(
    Illuminate\Contracts\Console\Kernel::class,
    App\Console\Kernel::class
);

$app->singleton(
    Illuminate\Contracts\Debug\ExceptionHandler::class,
    App\Exceptions\Handler::class
);

/*
|--------------------------------------------------------------------------
| Return The Application
|--------------------------------------------------------------------------
|
| This script returns the application instance. The instance is given to
| the calling script so we can separate the building of the instances
| from the actual running of the application and sending responses.
|
*/

return $app;
