package ga.grishanya.grishka_admin_aga.diplom;

import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetData {

    public static Date  getDataFromString(String date_from_grishanya){
        String OldDate =date_from_grishanya.substring(0,19);
        Date NewDate=null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.getDefault());
        try{ NewDate=sdf.parse(OldDate);}
        catch (Exception e){}

        return NewDate;
    }

    public static String getData(String date){
        Date OldDate=getDataFromString(date);
        String NewDate=null;
        SimpleDateFormat sdf=new SimpleDateFormat("dd MMM ' 'HH:mm",Locale.getDefault());
        try{NewDate=sdf.format(OldDate);}
        catch(Exception e){}
        return NewDate;
    }
}
