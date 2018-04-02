package ga.grishanya.grishka_admin_aga.diplom;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface GrishanyaApi {

    @POST("auth/login/")
    @FormUrlEncoded
    Call <LoginResponse> request(@Field("username") String username, @Field("password") String password);
}
