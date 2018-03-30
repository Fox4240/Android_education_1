package ga.grishanya.grishka_admin_aga.diplom;

import android.app.Activity;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RegistrationActivity extends AppCompatActivity {

    Button finishRegistration;
    EditText regUserLogin;
    EditText regPassword;
    EditText regPasswordConfirm;

    static final String API_URL = "https://grishanya.ga/api/auth/registration/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        finishRegistration = (Button) findViewById(R.id.finishRegistrati);
        regUserLogin = (EditText) findViewById(R.id.regUserLogin);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regPasswordConfirm=(EditText) findViewById(R.id.regPasswordConfirm);

        View.OnClickListener oclBtnReg = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
            }
        };
        finishRegistration.setOnClickListener(oclBtnReg);
    }

    public void ComeBack(View view) {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void saveText(String infoForSave,String NameOfSave) {
        SharedPreferences MyPreferences;
        MyPreferences=getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);
        Editor editor = MyPreferences.edit();
        editor.putString(NameOfSave+"",infoForSave+"" );
        editor.commit();
    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://grishanya.ga/api/auth/registration/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("username", regUserLogin.getText().toString()+"");
                    jsonParam.put("password1", regPassword.getText()+"");
                    jsonParam.put("password2", regPasswordConfirm.getText()+"");

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        Log.i("PostResponse",stringBuilder.toString()) ;//it should be save
                        String response = stringBuilder.toString();
                        if(response == "") {
                            response = "THERE WAS AN ERROR";
                        }
                        try{
                            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();// парсим json
                            response = object.getString("key");
                        }
                        catch(JSONException e){}
                        Log.i("Key",response);

                        saveText(response+"","Key");

                        Intent intent = new Intent(RegistrationActivity.this, BaseActivity.class);
                        startActivity(intent);

                    }catch (Exception e){}

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }






}
