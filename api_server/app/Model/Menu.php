<?php namespace App\Model;

class Menu extends BaseModel
{
    protected $table='menus';
    protected $connection = 'common';
    protected $fillable = [ 'title', 'parent_id', 'url' ,'remark','position','status'];
    public $timestamps = false;
}
