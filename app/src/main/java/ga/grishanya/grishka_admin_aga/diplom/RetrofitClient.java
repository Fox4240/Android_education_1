package ga.grishanya.grishka_admin_aga.diplom;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.security.Key;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient extends Application {

    private static GrishanyaApi grishanyaApi;
    private Retrofit retrofit;
    private static String KeyToken;
    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://grishanya.ga/api/")//Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())//Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        grishanyaApi=retrofit.create(GrishanyaApi.class);//Создаем объект, при помощи которого будем выполнять запросы

        setKeyToken();



    }
    public void setKeyToken(){
        SharedPreferences MyPreferences;
        MyPreferences=getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        KeyToken=MyPreferences.getString("Key", "");;
    }

    public static String getKeyToken(){
        return KeyToken;
    }
    public static GrishanyaApi getGrishanyaApi(){
        return grishanyaApi;
    }
}

