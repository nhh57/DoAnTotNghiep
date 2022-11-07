package com.example.ecommerce.model.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatHelper {
    public String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(dateFormat);
    }
    public String formatDateTime(Timestamp dataTime){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateTimeFormat.format(dataTime);
    }
}
