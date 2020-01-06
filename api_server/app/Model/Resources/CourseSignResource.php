<?php
namespace App\Model\Resources;

use Illuminate\Http\Resources\Json\Resource;
class CourseSignResource extends Resource
{
    public function toArray($request)
    {
        $courses = $this->whenLoaded('course');
        return [
            'id' => $this->id,
            'member_id' => $this->member_id,
            'member' => new MemberResource($this->whenLoaded('member')),
            'sign_at' => $this->sign_at,
            'sign_username' => $this->sign_username,
            'status' => $this->status,
            'points' => $this->points,
            'course_id' => $this->course_id,
//            'course' => $this->whenLoaded('course'),
            'user_name' => $courses ?  $this->course->user_name : '',
            'course_date' => $courses ?  $this->course->date : '',
            'course_start_at' => $courses ? $this->course->start_at : '',
            'product_name' => $this->whenLoaded('product') ?  $this->product->name : '',
        ];
    }
}