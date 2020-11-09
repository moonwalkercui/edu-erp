$(function () {
  "use strict";
  if ($("#editor").length > 0) {
    tinymce.init({
      selector: "textarea#editor",
      menubar: false,
      statusbar: false,
      // theme: "modern",
      // height: 300,
      // plugins: [
      //   "image lists charmap preview hr fullscreen  table contextmenu textcolor media link"
      // ],
      // language: 'zh_CN',
      // menubar: false,
      // toolbar_items_size: 'small',
      // toolbar1: "mybutton | undo redo | bold italic underline strikethrough | forecolor backcolor | alignleft aligncenter alignright | fontsizeselect | table image media link  | inserttime preview ",
      // style_formats: [
      //   {title: '粗体', inline: 'b'},
      //   {title: '红字', inline: 'span', styles: {color: '#ff0000'}},
      //   {title: '红标题', block: 'h1', styles: {color: '#ff0000'}},
      //   {title: '样式1', inline: 'span', classes: 'example1'},
      //   {title: '样式2', inline: 'span', classes: 'example2'},
      //   {title: '表格样式'},
      //   {title: 'Table row 1', selector: 'tr', classes: 'tablerow1'}
      // ],
      // setup: function (editor) {
      //   // 定义按钮，
      //   editor.addButton('mybutton', {
      //     text: '图库',
      //     icon: false,
      //     onclick: function () {
      //       // 这里点击后会插入一句话
      //       divEditorBtnFunc(editor);
      //     }
      //   })
      // }
    });
  }
  // function doy() {
  //     var tmp = tinymce.get('elm1').getContent();      //此函数可获得编辑框内容
  //     var tmp2 = tinymce.get('elm2').getContent();
  //     $('textarea').hide()
  //     $('.activityshow_box').show();
  //     $('.activityshow_box').append(tmp);
  //     tinymce.activeEditor.setContent("<p style='color:red;'>这只是个测试</p>");     //设置编辑框内容   Jquery.text('内容')方法也可以达到设置编辑框内容目的
  //     alert(tmp);
  //     alert(tmp2);
  // }
});