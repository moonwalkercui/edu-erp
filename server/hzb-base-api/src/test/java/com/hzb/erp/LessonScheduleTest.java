package com.hzb.erp;

import com.hzb.erp.common.service.LessonScheduleService;
import com.hzb.erp.common.service.LessonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class LessonScheduleTest {

    @Autowired
    private LessonScheduleService lessonScheduleService;
    @Autowired
    private LessonService lessonService;

    @Test
    void test1() {

        lessonService.closeLesson();

        // 检查冲突
//        List<Long> res = lessonScheduleService.checkConflict(Arrays.asList(53L));
//
//        for (Long id : res) {
//            System.out.println(id);
//        }


//        LocalDate startDate = LocalDate.of(2021, 9,20);
//        LocalDate endDate = LocalDate.of(2021, 10,9);
//
//        boolean excludeHoliday = true;
//
//        List<Integer> weekDays = new ArrayList<Integer>(){{
//            add(1);
//            add(2);
//            add(3);
//            add(5);
//        }};
//
//
//        List<LocalDate> res = new ArrayList<>();
//        List<LocalDate> dates = DateTool.findEveryDates(startDate, endDate);
//
//        for(LocalDate date : dates) {
//            // 如果星期里包含则追加
//            if(weekDays.contains( date.getDayOfWeek().getValue())) {
//                // 如果不排除假日 直接追加;
//                if(!excludeHoliday) {
//                    res.add(date);
//                } else {
//                    // 如果开启了排除假日 且 不是节日则追加;
//                    if(!DateTool.isHoliday(DateTool.localDateToDate(date))) {
//                        res.add(date);
//                    }
//                }
//
//            }
//        }
//
//        System.out.println("=======res======");
//        System.out.println(res);

    }

}
