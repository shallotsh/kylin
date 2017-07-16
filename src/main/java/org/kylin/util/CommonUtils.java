package org.kylin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huangyawu
 * @date 2017/7/16 下午5:08.
 */
public class CommonUtils {

    public static String getCurrentTimeString(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getCurrentDateString(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}
