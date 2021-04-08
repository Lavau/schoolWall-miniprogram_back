package top.leeti.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampUtil {

    public static String timeStamp(Date date) {
        // 获取当前时间戳
        Date newDate = new Date();

        // 计算时间的差值
        long timeDifferenceValue = newDate.getTime() - date.getTime();
        long second = timeDifferenceValue / 1000;
        long minute = second / 60;
        long hour = minute / 60;

        // 判断后，返回相应的值
        if (second < 60) {
            return String.format("%d 秒前", second);
        } else if (minute < 60) {
            return String.format("%d 分钟前", minute);
        } else if (hour < 24) {
            return String.format("%d 小时前", hour);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return dateFormat.format(date);
        }
    }
}
