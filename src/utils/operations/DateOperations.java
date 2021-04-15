package utils.operations;

import controllers.main_controller;
import javafx.scene.control.DatePicker;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateOperations {
    public static String longFormatter(long date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date _date = new Date(date);
        return formatter.format(_date);
    }
    public static String getLastEntegDate(String format) throws IOException, JSONException {
        long val = propertiesOperations.getPropOBJ().getLong("lastdatetimelong");
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date(val);
        return formatter.format(date);
    }

    public static void SaveLastEntegDate(String hour, DatePicker date){
        main_controller ctrl = new main_controller();
        String hour1 = String.valueOf(hour.charAt(0));
        String hour2 = String.valueOf(hour.charAt(1));
        String _hour = hour1+hour2;
        String min1 = String.valueOf(hour.charAt(3));
        String min2 = String.valueOf(hour.charAt(4));
        String _min = min1+min2;
        String sec1 = String.valueOf(hour.charAt(6));
        String sec2 = String.valueOf(hour.charAt(7));
        String _sec = sec1+sec2;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, date.getValue().getDayOfMonth());
        cal.set(Calendar.MONTH, date.getValue().getMonthValue());
        cal.set(Calendar.YEAR, date.getValue().getYear());
        cal.set(Calendar.HOUR, Integer.parseInt(_hour));
        cal.set(Calendar.MINUTE, Integer.parseInt(_min));
        cal.set(Calendar.SECOND, Integer.parseInt(_sec));
        long millis = cal.getTimeInMillis();
        propertiesOperations.SaveLastEntegDateLong(millis);
    }
}
