var optionsCtrl = {
    $ele: $("#option-list"),
    tr: function (i, content) {
        content = content || '';
        return `
             <tr>
                <td>` + String.fromCharCode((65+i)) +`</td>
                <td>
                    <textarea name="options[]" class="option-editor" rows="2">${content}</textarea>
                </td>
                <td class="right aligned">
                    <a style="cursor: pointer" class="del-q-btn" data-index="${i}">删除</a>
                </td>
             </tr>`;
    },
    renderList: function(list) {
        optionsCtrl.$ele.empty();
        var newIndex, html;
        for (var i in list) {
            newIndex = optionsCtrl.$ele.find('tr').length;
            html = optionsCtrl.tr(newIndex, list[i].body);
            optionsCtrl.$ele.append(html);
        }
    },
    add: function() {
        var newIndex = optionsCtrl.$ele.find('tr').length;
        var html = optionsCtrl.tr(newIndex);
        optionsCtrl.$ele.append(html);
        editorInit('.option-editor');
    },
    count: function() {
        return optionsCtrl.$ele.find('tr').length;
    },
    del: function(i) {
        optionsCtrl.$ele.find('tr').eq(i).remove();
        optionsCtrl.updateIndex();
    },
    updateIndex:function(){
        optionsCtrl.$ele.find('tr').each(function(i){
            $(this).find('td:first').html(String.fromCharCode((65+i)))
            $(this).find('td:last').find('a').attr('data-index', i)
        })
    }
}

$(function () {
    editorInit('.option-editor');

    $('.options-box').hide();
    $('#options-1').show();

    $(':radio[name=question_type]').change(function () {
        var v = $(this).val();
        $('.options-box').hide();
        $('#options-' + v).show();
        if (v == 1 || v == 3) {
            $('.options-box-ra').show();
        } else {
            $('.options-box-ra').hide();
        }
    });

    optionsCtrl.$ele.on('click', '.del-q-btn', function(){
        var ind = parseInt($(this).data('index'));
        console.log(ind)
        optionsCtrl.del(ind)
    })
});
