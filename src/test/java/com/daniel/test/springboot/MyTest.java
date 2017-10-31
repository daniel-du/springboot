package com.daniel.test.springboot;

import com.daniel.test.springboot.util.DateUtil;
import org.junit.Test;

import java.util.*;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/10/31 14:39
 */
public class MyTest {

    @Test
    public void testOptional() {
        String string = "aaa";

        Optional.ofNullable(string).ifPresent(System.out::println);
        Optional.ofNullable(string).ifPresent(s -> System.out.println(s));

        System.out.println(Optional.ofNullable(string).isPresent());
        Optional.ofNullable(string).filter(s -> !s.isEmpty());

        System.out.println("1----------------------");
        Map map = new HashMap<>();
        map.put("a", "aa");
        Map mapElse = new HashMap<>();
        mapElse.put("b", "bb");

        map.put("a", "a");
        Map map2 = Optional.ofNullable(map).filter(map1 -> map1.get("a").equals("aa")).orElse(mapElse);
        System.out.println(map2);

        System.out.println("2-------------------------");
        map.clear();
        String result = (String) Optional.ofNullable(map).filter(map1 -> !map1.isEmpty()).map(map1 -> map1.get("a")).orElse("bb");
        System.out.println(result);

        System.out.println("3-------------------------");
        Optional.ofNullable(map).map(map1 -> map1.get("a")).orElseThrow(() -> new RuntimeException("null pointer exception"));


    }

    /**
     * 崔旭值班日期计算器
     */
    @Test
    public void cuiXuZhiBanRiQi() {
        System.out.println("**********去崔旭家蹭饭小程序**********");
        int i = 0;
        Date date = new Date();
        while (i < 100) {
            if (i == 0) {
                getResult(date);
            } else {
                Date nextDate = DateUtil.addDay(date, 9 * i);
                getResult(nextDate);
            }
            i++;
        }
    }

    public static void getResult(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day < 0) {
            day = 0;
        }
        System.out.println("崔旭值班的日子!!!哈哈哈哈哈~~~~~~日期: " + DateUtil.formatDate(date) + " 星期: " + weekDays[day]);
        if (!weekDays[day].equals("星期日") && !weekDays[day].equals("星期六")) {
            // 1-5
            int saturDay = 6 - day;
            Date saturDayDate = DateUtil.addDay(date, saturDay);
            int sunDay = 7 - day;
            Date sunDayDate = DateUtil.addDay(date, sunDay);
            System.out.println("蹭饭日期, 周六: " + DateUtil.formatDate(saturDayDate)
                    + " 周日: " + DateUtil.formatDate(sunDayDate) + " 崔旭不值班, 可以去崔旭家蹭饭~~~~~~捏哈哈哈~~~~~~");

        }
    }
}
