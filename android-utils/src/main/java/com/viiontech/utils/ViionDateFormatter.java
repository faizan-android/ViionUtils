package com.viiontech.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
public class ViionDateFormatter {
    private final String TAG ="viionFormatter";
    public static final int  day_month_year = 0;
    public static final int  year_month_day = 1;
    public static final int  _12_hours_time = 4;
    public static final int  _24_hours_time = 5;
    public static final int  month_name= 6;
    public static final int  full_month_name = 7;
    public static final int  full_day_name = 8;
    public static final int  day_month_year_time_12_hours = 9;
    public static final int  day_month_year_time = 10;
    public static final int  time_day_month_year = 11;
    public static final int  year_month_day_time_12_hours = 12;
    public static final int  year_month_day_time = 13;
    public static final int  simple_date = 14;

    private String[] patterns = new String[]{
            "dd-MM-yyyy","yyyy-MM-dd","dd/MM/yyyy","yyyy/MM/dd", //date formatter,0,1,2,3
            "hh:mm:ss a","HH:mm:ss", //time formatter,4,5
            "MMM","MMMM", //month name formatter,6,7
            "E", //day name,8
            "dd-MM-yyyy hh:mm:ss a","dd-MM-yyyy HH:mm:ss", "HH:mm:ss dd-MM-yyyy",//9,10,11
            "yyyy-MM-dd hh:mm:ss a","yyyy-MM-dd HH:mm:ss",//12,13
            "dd MMM, yyyy"//14
    };

    private static ViionDateFormatter instance = new ViionDateFormatter();
    public static ViionDateFormatter getInstance(){
        return instance;
    }
    private ViionDateFormatter(){}

    public String convertTimeStamp(@NonNull String timeStamp,int pattern){
        return getDateString(getDate(timeStamp),pattern);
    }
    public Date getDate(@NonNull String dateString){

        for (String pattern : patterns) {
            try {
                Date temp = getFormatter(pattern).parse(dateString);
                if (temp != null && dateString.equalsIgnoreCase(getFormatter(pattern).format(temp))){
                    return temp;
                }
            } catch (ParseException e) {
                Log.d(TAG, dateString+" can't matched with "+pattern);
            }
        }
        //return default date time
        return Calendar.getInstance().getTime();
    }
    public String getDateString(int formatter){
        return getDateString(Calendar.getInstance().getTime(),formatter);
    }
    public String getDateString(@NonNull Date date,int formatter){
        return getFormatter(patterns[formatter]).format(date);
    }
    public String getTimeIn24Hours(){
        return getTime(_24_hours_time);
    }
    public String getTimeIn12Hours(){
        return getTime(_12_hours_time);
    }
    private String getTime(int type){
        return getTime(Calendar.getInstance().getTime(),type);
    }
    public String getTime(@NonNull Date date, int type){
        switch (type){
            case _12_hours_time:
                return getFormatter(patterns[_12_hours_time]).format(date);
            case _24_hours_time:
                return getFormatter(patterns[_24_hours_time]).format(date);

        }
        return null;
    }
    public String getFullMonthName(){
        return getFullMonthName(Calendar.getInstance().getTime());
    }
    public String getFullMonthName(@NonNull Date date){
        return getFormatter(patterns[full_month_name]).format(date);
    }
    public String getMonthName(){
        return getMonthName(Calendar.getInstance().getTime());
    }
    public String getMonthName(@NonNull Date date){
        return getFormatter(patterns[month_name]).format(date);
    }
    public String getDayName(){
        return getDayName(Calendar.getInstance().getTime());
    }
    public String getDayName(@NonNull Date date){
        return getFormatter(patterns[full_day_name]).format(date);
    }

    public String getDateTimeString(@NonNull Date date,@NonNull String dateFormatter){
        if(dateFormatter.isEmpty())
            return null;

        return getFormatter(dateFormatter).format(date);
    }
    public String getDifference(@NonNull Date date){
        Calendar timeStampCal = Calendar.getInstance();
        timeStampCal.setTime(date);
        String diffStr = "";

        long diffMinutes = TimeUnit.MINUTES.convert(Calendar.getInstance().getTimeInMillis() - timeStampCal.getTimeInMillis(), TimeUnit.MILLISECONDS);
        if(diffMinutes < 0){
            diffStr = getDateString(timeStampCal.getTime(),simple_date);
        }else if(diffMinutes == 0)
            diffStr = "Now";
        else if(diffMinutes < 60 )
            diffStr = diffMinutes +" Minutes Ago";
        else {
            long diffHours = TimeUnit.HOURS.convert(Calendar.getInstance().getTimeInMillis() - timeStampCal.getTimeInMillis(), TimeUnit.MILLISECONDS);
            if(diffHours == 0)
                diffStr = "1 Hour Ago";
            else if(diffHours < 24)
                diffStr = diffHours +" Hour Ago";
            else
            {
                long diffDay = TimeUnit.DAYS.convert(Calendar.getInstance().getTimeInMillis() - timeStampCal.getTimeInMillis(), TimeUnit.MILLISECONDS);
                if(diffDay == 0)
                    diffStr = "1 Day Ago";
                else{
                    if(diffDay > 3)
                        diffStr = getFormatter("MMMM dd, yyyy").format(timeStampCal.getTime());
                    else
                        diffStr = diffDay +" Days Ago";
                }
            }
        }
        return diffStr;
    }
    public String getDifference(@NonNull String timeStamp){
        return getDifference(getDate(timeStamp));
    }
    private SimpleDateFormat getFormatter(String pattern){
        return  new SimpleDateFormat(pattern,Locale.ENGLISH);
    }
}
