package cn.cloud.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileWayUtil {
    public static String getPathByNowDate(String realPath) {
        String dataPathSoure = getStringByDate();
        String[] datePaths = dataPathSoure.split("-");//转换为yyyy/MM/dd
        return realPath+File.separator+datePaths[0]+
                File.separator+datePaths[1]+File.separator+datePaths[2];
    }
    public static String getStringByDate(){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        return date.format(new Date());//得到格式为yyyy-MM-dd格式的日期转化为字符串
    }
    public static String getFileNameByRandom(String filename){
        Integer randomNum = getRandomNum();
        String[] split = filename.split("\\.");
        String[] filenames =  filename.split("\\."+split[split.length-1]);
        return filenames[0]+"--"+randomNum+"."+split[split.length-1];
    }
    private static Integer getRandomNum(){

        int i = UUID.randomUUID().toString().hashCode();
        if(i<0){
            i=(-i);
        }
        i%=100000;
        if(i<10000) {
            i *= 10;
        }
        return i;
    }
}
