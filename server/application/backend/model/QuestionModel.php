<?php
namespace app\backend\model;

use app\common\model\Question;
use app\common\model\Test;

class QuestionModel extends Question
{
    static function getByTestId($test_id)
    {
        $test = Test::find($test_id);
        if(!$test) return [];
        return $test->question;
    }
}