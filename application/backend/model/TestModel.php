<?php
namespace app\backend\model;

use app\common\model\Test;
use app\common\model\TestCategory;
use think\Db;

class TestModel extends Test
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $sections = SectionModel::column("title",'id');
        $data = [
            [
                'type' => 'input',
                'name' => 'title',
                'label' => '标题',
                'require' => true,
            ], [
                'type' => 'select',
                'name' => 'category_id',
                'label' => '所属分类',
                'options' => TestCategoryModel::getTreeColumn(),
                'require' => true,
            ], [
                'type' => 'textarea',
                'name' => 'description',
                'label' => '试卷说明',
            ], [
                'type' => 'mselect',
                'name' => 'sections',
                'label' => '关联章节',
                'options' => self::categoryWrap($sections, false)
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

    static function updateSectionsRel(int $id, array $section_ids)
    {
        Db::name('section_test')->where('test_id', $id)->delete();
        if(count($section_ids) > 0 ) {
            $insert = [];
            foreach ($section_ids as $sid) {
                $insert[] = [
                    'test_id' => $id,
                    'section_id' => $sid,
                ];
            }
            Db::name('section_test')->insertAll($insert);
        }
    }
}