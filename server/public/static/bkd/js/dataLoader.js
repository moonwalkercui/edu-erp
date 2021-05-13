const ApiBaseUrl = 'http://sj.xinyangedu.com';
const Apis = {
    getKnowledgeList : ApiBaseUrl + '/backend/knowledge/loadData.html',
    editKnowledge : ApiBaseUrl + '/backend/knowledge/getList.html',
    h5List : ApiBaseUrl + '/backend/knowledge/h5List.html?act=select',
    getTestList : ApiBaseUrl + '/backend/test/getList.html?act=select',
    loadTestList : ApiBaseUrl + '/backend/test/loadData.html',
    loadH5Data : ApiBaseUrl + '/backend/knowledge/loadH5Data.html',
    loadH5DataByKnowledgeIds : ApiBaseUrl + '/backend/knowledge/loadH5DataByKnowledgeIds.html',
    getQuestionList : ApiBaseUrl + '/backend/test/getQuestionList.html',
    saveQuestionGroup : ApiBaseUrl + '/backend/test/saveQuestionGroup.html',
    loadGroupQuestion : ApiBaseUrl + '/backend/test/loadGroupQuestion.html',
    // loadTestQuestions : ApiBaseUrl + '/backend/test/loadTestQuestions.html',
    updateTestQuestions : ApiBaseUrl + '/backend/test/updateTestQuestions.html',
    addTestPart : ApiBaseUrl + '/backend/test/addTestPart.html',
    updateTestPart : ApiBaseUrl + '/backend/test/updateTestPart.html',
    updateTestQuestionsScore : ApiBaseUrl + '/backend/test/updateTestQuestionsScore.html',
    sortTestQuestions : ApiBaseUrl + '/backend/test/sortTestQuestions.html',
    delQuestion : ApiBaseUrl + '/backend/test/delQuestion.html',
    sortQuestion : ApiBaseUrl + '/backend/test/sortQuestion.html',
    // downQuestion : ApiBaseUrl + '/backend/test/downQuestion.html',
};

function loadKnowledgeList(cb) {
    ajaxRequest(Apis.getKnowledgeList, {}, function(res) {
        if(handleErr(res)) {
            cb(res.data);
        }
    });
}
// function loadTestList(cb) {
//     ajaxRequest(Apis.getTestList, {}, function(res) {
//         if(handleErr(res)) {
//             cb(res.data);
//         }
//     });
// }

function getQuestionList(param, cb) {
    ajaxRequest(Apis.getQuestionList, param, function(res) {
        if(handleErr(res)) {
            cb(res.data);
        }
    });
}
function loadGroupQuestion(param, cb) {
    ajaxRequest(Apis.loadGroupQuestion, param, function(res) {
        if(handleErr(res)) {
            cb(res.data);
        }
    });
}
// function loadQuestion(param, cb) {
//     ajaxRequest(Apis.loadQuestion, param, function(res) {
//         if(handleErr(res)) {
//             cb(res.data);
//         }
//     });
// }
function delQuestion(param, cb) {
    ajaxRequest(Apis.delQuestion, param, function(res) {
        if(handleErr(res)) {
            cb(res.data);
        }
    });
}
function sortQuestion(param, cb) {
    ajaxRequest(Apis.sortQuestion, param, function(res) {});
}
// function downQuestion(param, cb) {
//     ajaxRequest(Apis.downQuestion, param, function(res) {
//         if(handleErr(res)) {
//             cb(res.data);
//         }
//     });
// }
function handleErr(res) {
    if(res.result !== 'success') {
        layer.msg(res.msg || '出错');
        return false;
    }
    return true;
}
