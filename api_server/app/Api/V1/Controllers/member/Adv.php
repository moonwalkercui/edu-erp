<?php
namespace App\Api\V1\Controllers\member;

use App\Model\Advertisement;
use App\Model\Notification;
use App\Model\Resources\ProductResource;
use App\Model\Product as ProductModel;
use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Adv extends Base
{
    public function find(Request $request)
    {
        $data = [];
        if($request->filled('id')) $data = Advertisement::getById($request->id);
        if($request->filled('position')) $data = Advertisement::getByPosition($request->position);
        return $this->fetch($data);
    }
    public function notifications(Request $request)
    {
        $where = [];
        if($request->filled('id')) {
            $where['id'] = $request->id == "p_0" ? 94 : $request->id;
            $data = Notification::where($where)->first();
            return $this->fetch($data);
        }
        if($request->filled('is_index') && $request->is_index == 1) {
            $data['tops'] = Notification::where('is_top',1)->get();
            $data['popup'] = Notification::where('is_popup',1)->orderBy('updated_at','desc')->limit(1)->first();
            return $this->fetch($data);
        }
        if($request->filled('page'))
            $data = Notification::where($where)->orderBy('updated_at','desc')->paginate($request->input('per_page', config('system.number_paginate')));
        return $this->fetch($data);
    }

}