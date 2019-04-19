package cn.cloud.util;


import java.util.Date;

public class TimeCompute {
    public static int getTimeDelta(Date date1,Date date2){
        long timeDelta=(date1.getTime()-date2.getTime())/1000;//单位秒
        int seconds=timeDelta>0?(int)timeDelta:(int)Math.abs(timeDelta);
        return seconds;
    }



}
