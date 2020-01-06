<?php
namespace App\Api\V1\Controllers;

use App\Exceptions\ApiException;
use App\Http\Controllers\Controller;
use App\Service\Api\LogService;
use App\Service\ApiTrait;
use Illuminate\Http\Request;

class Base extends Controller
{
    use ApiTrait;

    public function export($data, $header, $file_name)
    {
        \App\Service\ExcelService::exportExcel(
            $data,
            $header,
            $file_name . '_' . date('mdHis') . '.xlsx'
        );
        LogService::userLog('导出数据', $file_name );
//        \App\Service\ExcelService::exportExcel2(
//            [
//                ['id' => 1, 'title' => 'ceshi1'],
//                ['id' => 2, 'title' => 'ceshi2'],
//                ['id' => 3, 'title' => 'ceshi3'],
//            ],
//            ['id' => '编号', 'title' => '测试title'],
//            'new_excel.xlsx'
//        );
    }

}