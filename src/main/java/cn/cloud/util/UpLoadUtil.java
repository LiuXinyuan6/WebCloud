package cn.cloud.util;

public class UpLoadUtil {
    private static final Boolean NOT_PASS_FIlE=false;
    private static final Boolean PASS_FIlE=true;
    public static final String[] DEFAULT_TYPES = new String[]{"rar","java","zip","jpg","png","txt","jar"};

    public static Boolean judgeFileType(String fileSuffix,String[] arrs) {
        Boolean judge=NOT_PASS_FIlE;
        System.out.println(DEFAULT_TYPES.length);
        if(arrs.length==0){
            arrs=DEFAULT_TYPES;
        }else {

            for (int i=0;i<arrs.length;i++){
                System.out.println(arrs[i]);
                if (fileSuffix.equals(arrs[i])){
                    judge=PASS_FIlE;
                    break;
                }
            }
        }
        return judge;
    }
}
