package ga.grishanya.grishka_admin_aga.diplom;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

//В манифесте не записан, надо что-то придумать, не работает =(


public class SafeLoadClass extends Activity {
/*
    private static SafeLoadClass safeLoadClass = null;

    public static SafeLoadClass getClient(String baseUrl) {
        if (safeLoadClass==null) {
            safeLoadClass = new SafeLoadClass();

        }
        return safeLoadClass;
    }
*/
    public void saveText(String infoForSave,String NameOfSave) {
        SharedPreferences MyPreferences;
        MyPreferences=getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = MyPreferences.edit();
        editor.putString(NameOfSave+"",infoForSave+"" );
        editor.commit();
    }

    public String loadRes(String Key){
        SharedPreferences MyPreferences;
        MyPreferences=getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);
        return MyPreferences.getString(Key+"", "");
    }
}
