<?php

namespace App\Service;

use PhpOffice\PhpSpreadsheet\Spreadsheet;
use PhpOffice\PhpSpreadsheet\Writer\Xlsx;
use PhpOffice\PhpSpreadsheet\Reader\Xls;
use PhpOffice\PhpSpreadsheet\IOFactory;
use PhpOffice\PhpSpreadsheet\Cell\Coordinate;
use PhpOffice\PhpSpreadsheet\Worksheet\PageSetup;
use PhpOffice\PhpSpreadsheet\Cell\DataType;
use PhpOffice\PhpSpreadsheet\Style\Fill;
use PhpOffice\PhpSpreadsheet\Style\Color;
use PhpOffice\PhpSpreadsheet\Style\Alignment;
use PhpOffice\PhpSpreadsheet\Style\Border;
use PhpOffice\PhpSpreadsheet\Style\NumberFormat;

/**
 * Created by PhpStorm.
 * User: 541720500@qq.com
 */

//    $spreadsheet = new \PhpOffice\PhpSpreadsheet\Spreadsheet();
//    $sheet = $spreadsheet->getActiveSheet();
//    $sheet->setCellValue('A1', 'Hello World !');
//
//    $writer = new PhpOffice\PhpSpreadsheet\Writer\Xlsx($spreadsheet);
//    $writer->save('hello world.xlsx');

class ExcelService
{
    /*
     * $data 数据库结果集
     * $header 数据表头，格式：[ 'id' => '编号', 'title' => '标题', ... ] 键为字段名 值为表头
     *
     * */
    public static function exportExcel($data, $header, $filename='')
    {
        if(empty($data)) {
            echo "导出数据为空";
            die;
//            throw new \Exception('导出数据为空');
        }
        if(empty($header)) {
            echo "未设置表头";
            die;
//            throw new \Exception('未设置表头');
        }

        $header_len = count($header);
        $body_len = count($data[0]);
//        if($header_len < $body_len) throw new \Exception('表头个数不能少于单行数据个数');
        $fileds = array_keys($header);
        $titles = array_values($header);

        $spreadsheet = new Spreadsheet();
        $worksheet = $spreadsheet->getActiveSheet();

        $worksheet->setTitle('校源系统数据导出');

        for ($i = 0; $i < $header_len; $i++) {
            $worksheet->setCellValueByColumnAndRow($i+1, 1, $titles[$i]);
        }
        $body_len = count($data);
        for ($i=0; $i < $body_len; $i++) { // 行
            for ($j=0; $j < $header_len; $j++) {
                $worksheet->setCellValueByColumnAndRow($j+1, $i+2, $data[$i][$fileds[$j]]);
            }
        }
        // 下载
        $filename = $filename ?: time();
        header('Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
        header('Content-Disposition: attachment;filename="'.$filename.'"');
        header('Cache-Control: max-age=0');
        $writer = IOFactory::createWriter($spreadsheet, 'Xlsx');
        $writer->save('php://output');
    }

    /**
     * 使用PHPEXECL导入
     *
     * @param string $file 文件地址
     * @param int $sheet 工作表sheet(传0则获取第一个sheet)
     * @param int $columnCnt 列数(传0则自动获取最大列)
     * @param array $options 操作选项
     *                          array mergeCells 合并单元格数组
     *                          array formula    公式数组
     *                          array format     单元格格式数组
     *
     * @return array
     * @throws Exception
     */
    public static function importExecl(string $file = '', int $sheet = 0, int $columnCnt = 0, &$options = [])
    {
        try {
            /* 转码 */
            $file = iconv("utf-8", "gb2312", $file);

            if (empty($file) OR !file_exists($file)) {
                throw new \Exception('文件不存在!');
            }

            /** @var Xlsx $objRead */
            $objRead = IOFactory::createReader('Xlsx');
            if (!$objRead->canRead($file)) {
                /** @var Xls $objRead */
                $objRead = IOFactory::createReader('Xls');
                if (!$objRead->canRead($file)) {
                    throw new \Exception('只支持导入Excel文件！');
                }
            }

            /* 如果不需要获取特殊操作，则只读内容，可以大幅度提升读取Excel效率 */
            empty($options) && $objRead->setReadDataOnly(true);
            /* 建立excel对象 */
            $obj = $objRead->load($file);
            /* 获取指定的sheet表 */
            $currSheet = $obj->getSheet($sheet);

            if (isset($options['mergeCells'])) {
                /* 读取合并行列 */
                $options['mergeCells'] = $currSheet->getMergeCells();
            }

            if (0 == $columnCnt) {
                /* 取得最大的列号 */
                $columnH = $currSheet->getHighestColumn();
                /* 兼容原逻辑，循环时使用的是小于等于 */
                $columnCnt = Coordinate::columnIndexFromString($columnH);
            }

            /* 获取总行数 */
            $rowCnt = $currSheet->getHighestRow();
            $data = [];

            /* 读取内容 */
            for ($_row = 1; $_row <= $rowCnt; $_row++) {
                $isNull = true;

                for ($_column = 1; $_column <= $columnCnt; $_column++) {
                    $cellName = Coordinate::stringFromColumnIndex($_column);
                    $cellId = $cellName . $_row;
                    $cell = $currSheet->getCell($cellId);

                    if (isset($options['format'])) {
                        /* 获取格式 */
                        $format = $cell->getStyle()->getNumberFormat()->getFormatCode();
                        /* 记录格式 */
                        $options['format'][$_row][$cellName] = $format;
                    }

                    if (isset($options['formula'])) {
                        /* 获取公式，公式均为=号开头数据 */
                        $formula = $currSheet->getCell($cellId)->getValue();

                        if (0 === strpos($formula, '=')) {
                            $options['formula'][$cellName . $_row] = $formula;
                        }
                    }

                    if (isset($format) && 'm/d/yyyy' == $format) {
                        /* 日期格式翻转处理 */
                        $cell->getStyle()->getNumberFormat()->setFormatCode('yyyy/mm/dd');
                    }

                    $data[$_row][$cellName] = trim($currSheet->getCell($cellId)->getFormattedValue());

                    if (!empty($data[$_row][$cellName])) {
                        $isNull = false;
                    }
                }

                /* 判断是否整行数据为空，是的话删除该行数据 */
                if ($isNull) {
                    unset($data[$_row]);
                }
            }

            return $data;
        } catch (\Exception $e) {
            throw $e;
        }
    }

    /**
     * Excel导出
     * @param array $datas 导出数据，格式['A1' => 'XXXX公司报表', 'B1' => '序号']
     * @param string $fileName 导出文件名称
     * @param array $options 操作选项，例如：
     *                           bool   print       设置打印格式
     *                           string freezePane  锁定行数，例如表头为第一行，则锁定表头输入A2
     *                           array  setARGB     设置背景色，例如['A1', 'C1']
     *                           array  setWidth    设置宽度，例如['A' => 30, 'C' => 20]
     *                           bool   setBorder   设置单元格边框
     *                           array  mergeCells  设置合并单元格，例如['A1:J1' => 'A1:J1']
     *                           array  formula     设置公式，例如['F2' => '=IF(D2>0,E42/D2,0)']
     *                           array  format      设置格式，整列设置，例如['A' => 'General']
     *                           array  alignCenter 设置居中样式，例如['A1', 'A2']
     *                           array  bold        设置加粗样式，例如['A1', 'A2']
     *                           string savePath    保存路径，设置后则文件保存到服务器，不通过浏览器下载
     */
//    public static function exportExcel(array $datas, string $fileName = '', array $options = []): bool
//    {
//        try {
//            if (empty($datas)) {
//                return false;
//            }
//            set_time_limit(0);
//            /** @var Spreadsheet $objSpreadsheet */
//            $objSpreadsheet = app(Spreadsheet::class);
//            /* 设置默认文字居左，上下居中 */
//            $styleArray = [
//                'alignment' => [
//                    'horizontal' => Alignment::HORIZONTAL_LEFT,
//                    'vertical' => Alignment::VERTICAL_CENTER,
//                ],
//            ];
//            $objSpreadsheet->getDefaultStyle()->applyFromArray($styleArray);
//            /* 设置Excel Sheet */
//            $activeSheet = $objSpreadsheet->setActiveSheetIndex(0);
//
//            /* 打印设置 */
//            if (isset($options['print']) && $options['print']) {
//                /* 设置打印为A4效果 */
//                $activeSheet->getPageSetup()->setPaperSize(PageSetup:: PAPERSIZE_A4);
//                /* 设置打印时边距 */
//                $pValue = 1 / 2.54;
//                $activeSheet->getPageMargins()->setTop($pValue / 2);
//                $activeSheet->getPageMargins()->setBottom($pValue * 2);
//                $activeSheet->getPageMargins()->setLeft($pValue / 2);
//                $activeSheet->getPageMargins()->setRight($pValue / 2);
//            }
//
//            /* 行数据处理 */
//            foreach ($datas as $sKey => $sItem) {
//                /* 默认文本格式 */
//                $pDataType = DataType::TYPE_STRING;
//
//                /* 设置单元格格式 */
//                if (isset($options['format']) && !empty($options['format'])) {
//                    $colRow = Coordinate::coordinateFromString($sKey);
//
//                    /* 存在该列格式并且有特殊格式 */
//                    if (isset($options['format'][$colRow[0]]) &&
//                        NumberFormat::FORMAT_GENERAL != $options['format'][$colRow[0]]) {
//                        $activeSheet->getStyle($sKey)->getNumberFormat()
//                            ->setFormatCode($options['format'][$colRow[0]]);
//
//                        if (false !== strpos($options['format'][$colRow[0]], '0.00') &&
//                            is_numeric(str_replace(['￥', ','], '', $sItem))) {
//                            /* 数字格式转换为数字单元格 */
//                            $pDataType = DataType::TYPE_NUMERIC;
//                            $sItem = str_replace(['￥', ','], '', $sItem);
//                        }
//                    } elseif (is_int($sItem)) {
//                        $pDataType = DataType::TYPE_NUMERIC;
//                    }
//                }
//
//                $activeSheet->setCellValueExplicit($sKey, $sItem, $pDataType);
//
//                /* 存在:形式的合并行列，列入A1:B2，则对应合并 */
//                if (false !== strstr($sKey, ":")) {
//                    $options['mergeCells'][$sKey] = $sKey;
//                }
//            }
//
//            unset($datas);
//
//            /* 设置锁定行 */
//            if (isset($options['freezePane']) && !empty($options['freezePane'])) {
//                $activeSheet->freezePane($options['freezePane']);
//                unset($options['freezePane']);
//            }
//
//            /* 设置宽度 */
//            if (isset($options['setWidth']) && !empty($options['setWidth'])) {
//                foreach ($options['setWidth'] as $swKey => $swItem) {
//                    $activeSheet->getColumnDimension($swKey)->setWidth($swItem);
//                }
//
//                unset($options['setWidth']);
//            }
//
//            /* 设置背景色 */
//            if (isset($options['setARGB']) && !empty($options['setARGB'])) {
//                foreach ($options['setARGB'] as $sItem) {
//                    $activeSheet->getStyle($sItem)
//                        ->getFill()->setFillType(Fill::FILL_SOLID)
//                        ->getStartColor()->setARGB(Color::COLOR_YELLOW);
//                }
//
//                unset($options['setARGB']);
//            }
//            /* 设置公式 */
//            if (isset($options['formula']) && !empty($options['formula'])) {
//                foreach ($options['formula'] as $fKey => $fItem) {
//                    $activeSheet->setCellValue($fKey, $fItem);
//                }
//
//                unset($options['formula']);
//            }
//            /* 合并行列处理 */
//            if (isset($options['mergeCells']) && !empty($options['mergeCells'])) {
//                $activeSheet->setMergeCells($options['mergeCells']);
//                unset($options['mergeCells']);
//            }
//
//            /* 设置居中 */
//            if (isset($options['alignCenter']) && !empty($options['alignCenter'])) {
//                $styleArray = [
//                    'alignment' => [
//                        'horizontal' => Alignment::HORIZONTAL_CENTER,
//                        'vertical' => Alignment::VERTICAL_CENTER,
//                    ],
//                ];
//
//                foreach ($options['alignCenter'] as $acItem) {
//                    $activeSheet->getStyle($acItem)->applyFromArray($styleArray);
//                }
//
//                unset($options['alignCenter']);
//            }
//
//            /* 设置加粗 */
//            if (isset($options['bold']) && !empty($options['bold'])) {
//                foreach ($options['bold'] as $bItem) {
//                    $activeSheet->getStyle($bItem)->getFont()->setBold(true);
//                }
//
//                unset($options['bold']);
//            }
//
//            /* 设置单元格边框，整个表格设置即可，必须在数据填充后才可以获取到最大行列 */
//            if (isset($options['setBorder']) && $options['setBorder']) {
//                $border = [
//                    'borders' => [
//                        'allBorders' => [
//                            'borderStyle' => Border::BORDER_THIN, // 设置border样式
//                            'color' => ['argb' => 'FF000000'], // 设置border颜色
//                        ],
//                    ],
//                ];
//                $setBorder = 'A1:' . $activeSheet->getHighestColumn() . $activeSheet->getHighestRow();
//                $activeSheet->getStyle($setBorder)->applyFromArray($border);
//                unset($options['setBorder']);
//            }
//
//            $fileName = !empty($fileName) ? $fileName : (date('YmdHis') . '.xlsx');
//
//            if (!isset($options['savePath'])) {
//                /* 直接导出Excel，无需保存到本地，输出07Excel文件 */
//                header('Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
//                header(
//                    "Content-Disposition:attachment;filename=" . iconv("utf-8", "GB2312//TRANSLIT", $fileName)
//                );
//                header('Cache-Control: max-age=0');//禁止缓存
//                $savePath = 'php://output';
//            } else {
//                $savePath = $options['savePath'];
//            }
//
//            ob_clean();
//            ob_start();
//            $objWriter = IOFactory::createWriter($objSpreadsheet, 'Xlsx');
//            $objWriter->save($savePath);
//            /* 释放内存 */
//            $objSpreadsheet->disconnectWorksheets();
//            unset($objSpreadsheet);
//            ob_end_flush();
//
//            return true;
//        } catch (\Exception $e) {
//            return false;
//        }
//    }


}