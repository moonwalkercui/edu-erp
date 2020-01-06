export const BaseDomain = 'api.域名'; // 
export const ServiceUrl = 'https://域名/'; //

export function getDomianPrefix()
{
  var api_prefix = '';
  var url = document.location.toString();
  var arrUrl = url.split("//");
  var start = arrUrl[1].indexOf(".");
  if(start == -1) {
    api_prefix = 'demo';
  } else {
    api_prefix = arrUrl[1].substring(0, start);
  }
  return api_prefix;
}
export function makeBaseUrl() {
  return 'https://' + BaseDomain + '/' + getDomianPrefix() + '/';
}
export function makeUploadUrl(withBaseUrl = false) {
  return withBaseUrl ? makeBaseUrl() + 'sys/uploader' : 'sys/uploader';
}
export function makeImportXlsUrl(withBaseUrl = false) {
  return withBaseUrl ? makeBaseUrl() + 'sys/import' : 'sys/import';
}