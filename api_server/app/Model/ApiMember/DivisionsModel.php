<?php

namespace App\Model\ApiMember;

use App\Model\Divisions;
use App\Model\Region;

class DivisionsModel extends Divisions
{
    public static function getRegionTree()
    {
        $regions = Region::getTree()[0]['_child']; // 从济南开始

        $ids = [];
        foreach ($regions[0]['_child'] as $r) {
            $ids[] = $r['id'];
        }
        $divisions = self::whereIn('region_id', $ids)->select('id','name','region_id')->get();

        foreach ($regions[0]['_child'] as $k => &$r) {
            $r['divisions'] = [];
            foreach ($divisions as $d) {
                if($r['id'] == $d->region_id) {
                    $r['divisions'][] = $d;
                }
            }
            if (empty($r['divisions'])) unset($regions[0]['_child'][$k]);
        }
        $regions[0]['_child'] = array_values($regions[0]['_child']);
        return $regions;
    }
}