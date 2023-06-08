package com.example.bluejackpharmacy.system.feature;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluejackpharmacy.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class UIHelper {

    public static void setColorAccent(Context ctx, TextView widget) {
        widget.setTextColor(ctx.getColor(R.color.acc_blue));

        Shader gradient = new LinearGradient(
                0,
                0,
                widget.getPaint().measureText(widget.getText().toString()),
                widget.getTextSize(),
                new int[]{
                        ctx.getColor(R.color.acc_blue),
                        ctx.getColor(R.color.acc_green)
                },
                new float[]{0, 1},
                Shader.TileMode.CLAMP
        );

        widget.getPaint().setShader(gradient);
    }

    public static void setColorAccent(Context ctx, Button widget) {
        widget.setTextColor(ctx.getColor(R.color.acc_blue));

        Shader gradient = new LinearGradient(
                0,
                0,
                widget.getPaint().measureText(widget.getText().toString()),
                widget.getTextSize(),
                new int[]{
                        ctx.getColor(R.color.acc_blue),
                        ctx.getColor(R.color.acc_green)
                },
                new float[]{0, 1},
                Shader.TileMode.CLAMP
        );

        widget.getPaint().setShader(gradient);
    }

    public static String getTimeGreetings() {
        DateFormat dateFormat = new SimpleDateFormat("HH");
        Calendar cal = Calendar.getInstance();
        int hour = Integer.parseInt(dateFormat.format(cal.getTime()));

        if(hour <= 10){
            return "Good Morning";
        }else if(hour <= 14){
            return "Good Day";
        }else if(hour <= 18){
            return "Good Afternoon";
        } else {
            return "Good Evening";
        }
    }

    public static String getCurrencyFormat(double value) {
        NumberFormat IDR = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        return "IDR " + IDR.format(value);
    }

    public static boolean liveValName(String text) {
        Pattern regex = Pattern.compile("^[a-zA-Z ]*.{5,25}$");
        return regex.matcher(text).matches();
    }

    public static boolean liveValEmail(String text) {
        if(text.length() > 50) {
            return false;
        }
        return text.toLowerCase().endsWith(".com") && text.contains("@");
    }

    public static boolean liveValPassword(String text) {
        int len = text.length();
        if(len < 8) {
            return false;
        } else if(len > 20) {
            return false;
        }

        boolean cAlpha = false, alpha = false, numeric = false;
        for(int i=0;i<len;i++) {
            if(text.charAt(i) >= '0' && text.charAt(i) <= '9'){
                numeric = true;
            }
            if(text.charAt(i) >= 'a' && text.charAt(i) <= 'z'){
                alpha = true;
            }
            if(text.charAt(i) >= 'A' && text.charAt(i) <= 'Z'){
                cAlpha = true;
            }
        }

        return cAlpha && alpha && numeric;
    }

    public static boolean liveValPhone(String text) {
        int len = text.length(), i = 0;
        if(text.charAt(0) == '+') {
            i++;
        }

        for(;i<len;i++) {
            if(text.charAt(i) == ' ' || text.charAt(i) == '-') {
                continue;
            } else if(text.charAt(i) < '0' || text.charAt(i) > '9'){
                return false;
            }
        }

        return text.length() <= 20;
    }
}
