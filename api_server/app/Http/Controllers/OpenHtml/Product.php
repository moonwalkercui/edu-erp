<?php

namespace App\Http\Controllers\OpenHtml;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Model\Product as ProductMod;

class Product extends Controller
{
    public function description($id)
    {
        $product = ProductMod::find($id);
//        return view('open.productDesc', ['data' => $product]);
    }
}
