package com.task.satu.rabbitsfarmer;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Satu on 2015-05-10.
 */ // helper
public class Helper {

    Context context;

    public Helper(Context context) {
        this.context = context;
    }
    public String parseDateToString(Date date, String dateFormat){

        String newDate="";
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        newDate=df.format(date);

        return newDate;
    }
}
