package ga.grishanya.grishka_admin_aga.diplom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

Button loginButton;
Button registationStratButton;
EditText editLogin;
EditText editPassword;
CheckBox rememberLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_password);
        editLogin=(EditText) findViewById(R.id.editLogin);
        loginButton=(Button) findViewById(R.id.loginButton);
        editPassword=(EditText) findViewById(R.id.editPassword);

        View.OnClickListener oclBtnTransl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitClient.getGrishanyaApi().request(editLogin.getText().toString()+"", editPassword.getText().toString()+"").enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        try{
                            Log.i("Succes",response.isSuccessful()+"");
                            String resp = response.body().getKey();
                            Log.i("Key",resp + "");
                            saveText(resp+"","Key");
                            Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                            startActivity(intent);
                        }
                        catch (NullPointerException e){}

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }

                });
            }
        };
        loginButton.setOnClickListener(oclBtnTransl);

        SharedPreferences mySharedPreferences = getSharedPreferences("My_preferences", Context.MODE_PRIVATE);
    }

    public void startRegistration(View view) {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }


    public String loadRes(String Key){
        SharedPreferences MyPreferences;
        MyPreferences=getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);
        return MyPreferences.getString(Key+"", "");
    }

    public void saveText(String infoForSave,String NameOfSave) {
        SharedPreferences MyPreferences;
        MyPreferences=getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = MyPreferences.edit();
        editor.putString(NameOfSave+"",infoForSave+"" );
        editor.commit();
    }
}
