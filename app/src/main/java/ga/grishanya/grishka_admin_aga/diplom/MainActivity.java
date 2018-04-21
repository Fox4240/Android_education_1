package ga.grishanya.grishka_admin_aga.diplom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//ToDo: Узнать как устанавливать авто отступ
//ToDo: Заполнение профиля после регистрации
//ToDo: Cмену пароля туда же
public class MainActivity extends AppCompatActivity {

Button loginButton;
Button registationStratButton;
EditText editLogin;
EditText editPassword;
CheckBox rememberLogin;
TextView errorTextView;
CheckBox rememberUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_password);
        getSupportActionBar().setTitle(R.string.main_activity_name);

        Log.i("knownUser",getLoggedUser()+"");
        if(getLoggedUser()){
            Intent intent = new Intent(MainActivity.this, BaseActivity.class);
            startActivity(intent);
        }

        rememberUser=(CheckBox) findViewById(R.id.rememberLogin);//ToDo добавить чек при переходе в след. окно
        editLogin=(EditText) findViewById(R.id.editLogin);
        loginButton=(Button) findViewById(R.id.loginButton);
        editPassword=(EditText) findViewById(R.id.editPassword);
        errorTextView=(TextView) findViewById(R.id.errorText);

        View.OnClickListener oclBtnTransl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest(editLogin.getText().toString()+"",editPassword.getText().toString()+"");
            }
        };
        loginButton.setOnClickListener(oclBtnTransl);

        rememberUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveText(rememberUser.isChecked()+"","rememberUser");
            }
        });

    }

    public void loginRequest(String username,String password){

            RetrofitClient.getGrishanyaApi().loginPOSTRequest(username + "", password + "").enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Log.i("Sucess", "s");
                    try {
                        if (!response.isSuccessful()) {
                            errorTextView.setVisibility(View.VISIBLE);
                        }

                        String resp = response.body().getKey();
                        Log.i("Key", resp + "");
                        Log.i("WasLoginSuccessful", response.isSuccessful() + "");

                        saveText(resp + "", "Key");

                        Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                        startActivity(intent);
                    } catch (NullPointerException e) {
                        errorTextView.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    errorTextView.setVisibility(View.VISIBLE);
                }

            });
    }

    public Boolean getLoggedUser(){
        if (loadRes("rememberUser").equals("true") && loadRes("rememberUser")!=null){return true;}
        return false;
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
