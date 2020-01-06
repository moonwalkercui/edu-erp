export function QQmap() {
  /* eslint-disable */
  return new Promise(function(resolve, reject) {
    window.mapinit = function() {
      resolve(qq); //注意这里
    };
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "https://map.qq.com/api/js?v=2.exp&key=L6BBZ-SW3W4-R7FUZ-DK6CP-7AVT6-SLBKD&callback=mapinit"; // 使用时改成自己的
    document.body.appendChild(script);

  });
}
