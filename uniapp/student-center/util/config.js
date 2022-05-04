// 生产模式下 debug 须为false
export const debug = true;
// 默认是和后端一个网址，所以使用了location的protocol和host，可以自行修改
export const domainUrl = debug ? 'http://localhost:8106/' : (window.location.protocol +'//'+window.location.host + '/');
// 后端上下文是app，这里设置
export const baseUrl = domainUrl + 'app/';
