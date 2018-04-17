package ga.grishanya.grishka_admin_aga.diplom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationActivity extends AppCompatActivity {

    Button finishRegistration;
    EditText regUserLogin;
    EditText regPassword;
    EditText regPasswordConfirm;
    EditText regEmail;
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        finishRegistration = (Button) findViewById(R.id.finishRegistrati);
        regUserLogin = (EditText) findViewById(R.id.regUserLogin);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regPasswordConfirm=(EditText) findViewById(R.id.regPasswordConfirm);
        errorTextView=(TextView) findViewById(R.id.errorTextRegistration);
        regEmail=(EditText) findViewById(R.id.editRegEmail);

        View.OnClickListener oclBtnReg = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginRequest(regUserLogin.getText().toString()+"",regEmail.getText().toString()+"",regPassword.getText().toString()+"",regPasswordConfirm.getText().toString()+"");
            }
        };
        finishRegistration.setOnClickListener(oclBtnReg);
    }

    public void loginRequest(String username,String email,String password,String confirmPassword){
        RetrofitClient.getGrishanyaApi().registrationPOSTRequest(username+"",email+"", password+"",confirmPassword+"").enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                try{
                    if(!response.isSuccessful()){errorTextView.setVisibility(View.VISIBLE);}

                    String resp = response.body().getKey();
                    Log.i("Key",resp + "");
                    Log.i("WasRegSuccessful",response.isSuccessful()+"");

                    saveText(resp+"","Key");

                    Intent intent = new Intent(RegistrationActivity.this, BaseActivity.class);
                    startActivity(intent);
                }
                catch (NullPointerException e){errorTextView.setVisibility(View.VISIBLE);}

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                errorTextView.setVisibility(View.VISIBLE);
            }

        });

    }

    public void ComeBack(View view) {

        finish();

    }

    public void saveText(String infoForSave,String NameOfSave) {
        SharedPreferences MyPreferences;
        MyPreferences=getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);
        Editor editor = MyPreferences.edit();
        editor.putString(NameOfSave+"",infoForSave+"" );
        editor.commit();
    }

}
