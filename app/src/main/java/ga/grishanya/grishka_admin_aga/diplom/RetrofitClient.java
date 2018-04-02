package ga.grishanya.grishka_admin_aga.diplom;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient extends Application {

    private static GrishanyaApi grishanyaApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://grishanya.ga/api/")//Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())//Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        grishanyaApi=retrofit.create(GrishanyaApi.class);//Создаем объект, при помощи которого будем выполнять запросы

    }

    public static GrishanyaApi getGrishanyaApi(){
        return grishanyaApi;
    }
}

