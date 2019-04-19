package cn.cloud.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date  getNowTime() throws ParseException {
        Date date = new Date();//获得系统时间.
        SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        String nowTime = sdf.format(date);
        Date time = sdf.parse( nowTime );
        return time;
    }
    public static Date  getTimeByString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd " );
        Date parse = sdf.parse(date);
        return parse;
    }
}
