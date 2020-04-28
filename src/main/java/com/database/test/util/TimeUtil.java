package com.database.test.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {


    public String getCurrentTime(){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=simpleDateFormat.format(calendar.getTime());
        return time;
    }

}
