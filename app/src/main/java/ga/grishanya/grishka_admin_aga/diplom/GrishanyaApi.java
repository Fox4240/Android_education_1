package ga.grishanya.grishka_admin_aga.diplom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface GrishanyaApi {

    @POST("auth/login/")
    @FormUrlEncoded
    Call <LoginResponse> loginPOSTRequest(@Field("username") String username, @Field("password") String password);

    @POST("auth/registration/")
    @FormUrlEncoded
    Call <LoginResponse> registrationPOSTRequest(@Field("username") String username, @Field("password1") String password,@Field("password2") String confirmPassword);

    @GET("tickets")
    Call <List<TicketResponse>> getTickets(@Header("Authorization") String auth);
}
