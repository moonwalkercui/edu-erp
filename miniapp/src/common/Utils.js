import Tips from '../common/Tips'
export default class Utils {
  static obj2url (obj) {
    if (typeof obj === 'undefined' || obj.length === 0) {
      return ''
    }
    let tmps = []
    for (let key in obj) {
      tmps.push(key + '=' + obj[key])
    }
    return tmps.join('&')
  }
  // 表单字段验证
  // formData:{name: jack, mobile: '15666323777'}
  // rules: {
  //  name: {
  //     required: true,
  //     minlength: 2,
  //     maxlength: 10,
  //  },
  //  mobile: {
  //      required: true,
  //      tel: true,
  //  }}
  static validateForm (data, rules) {
    try {
      for(let rule in rules) {
        // console.log('aaa', rule)
        // console.log('ddd', data[rule])
        for(let key in rules[rule]) {
          // console.log('ddd', key, data[rule])
          let d = data[rule]
          let t = rules[rule].title
          let v = rules[rule][key]
          switch(key) {
            case 'required': checkRequired(d, t, v); break;
            case 'minlength': checkLength(d, t, v, 'min'); break;
            case 'maxlength': checkLength(d, t, v, 'max'); break;
            case 'length': checkLength(d, t, v, 'eq'); break;
            case 'mobile': checkMobile(d, t, v); break;
            default: break
          }
        }
      }
    } catch( err ) {
      Tips.alert(err.message)
      return false
    }
    return true
    // 检查必填
    function checkRequired (d, t, v) {
      if (v === true && (d === '' || d === null || typeof d === 'undefined')) {
        throw new Error(`${t}为必须`)
      }
    }
    // 判断字符长度或数组元素个数
    function checkLength (d, t, num, mark) {
      let tip = (d instanceof Array) ? '元素' : '字符'
      if (mark == 'min' && d.length < num) {
        throw new Error(`${t}应至少${num}个${tip}`)
      }
      if (mark == 'max' && d.length > num) {
        throw new Error(`${t}应最多${num}个${tip}`)
      }
      if (mark == 'eq' && d.length != num) {
        throw new Error(`${t}应为${num}个${tip}`)
      }
    }
    function checkMobile (d, t, v) {
      var newReg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
      if (v === true && (d.length!=11 || !newReg.test(d))) {
        throw new Error(`请输入正确的${t}`)
      }
    }
  }
  static disableButtion(e) {
    console.log('按钮', e)
  }
}
