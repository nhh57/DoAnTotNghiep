package com.example.ecommerce.mvc.helper;

public class ReviewsHelper {
    public String getTime(long second){
        String time="";
        long min=second/60;
        long hours=min/60;
        long day=hours/24;
        long month=day/30;
        long year=month/365;
        if(second < 60){
            time=second + " giây trước";
        }else if(min < 60){
            time=min + " phút trước";
        }else if(hours < 24){
            time=hours + " giờ trước";
        }else if(day < 30){
            time=day + " ngày trước";
        }else{
            time=year + " năm trước";
        }
        return time;
    }
}
