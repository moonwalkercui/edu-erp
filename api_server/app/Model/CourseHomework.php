<?php

namespace App\Model;

class CourseHomework extends BaseModel
{
    protected $table='course_homework';
    public $timestamps = false;
    protected $fillable = [
        'course_id',
        'username',
        'caption',
        'image1',
        'image2',
        'image3',
    ];
    public function getImage1Attribute($value)
    {
        return addImagePrefix($value);
    }
    public function getImage2Attribute($value)
    {
        return addImagePrefix($value);
    }
    public function getImage3Attribute($value)
    {
        return addImagePrefix($value);
    }
    public static function saveHomework($username, $course_id, $caption, $images, $remark)
    {
        $homework = self::where('course_id', $course_id)->where('username', $username)->first();

        if ($homework) {
            $homework->caption = $caption;
            $homework->remark = $remark;

            $homework->image1 = isset($images[0]) ? cleanImagePrefix($images[0]) : null;
            $homework->image2 = isset($images[1]) ? cleanImagePrefix($images[1]) : null;
            $homework->image3 = isset($images[2]) ? cleanImagePrefix($images[2]) : null;
            $homework->updated_at = now();
            return $homework->save();
        } else {
            return self::insert([
                'course_id' => $course_id,
                'username' => $username,
                'caption' => $caption,
                'image1' => isset($images[0]) ? cleanImagePrefix($images[0]) : null,
                'image2' => isset($images[1]) ? cleanImagePrefix($images[1]) : null,
                'image3' => isset($images[2]) ? cleanImagePrefix($images[2]) : null,
                'created_at' => now(),
                'remark' => $remark,
            ]);
        }
    }
}
