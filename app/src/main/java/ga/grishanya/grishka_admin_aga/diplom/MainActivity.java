package ga.grishanya.grishka_admin_aga.diplom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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
        SharedPreferences mySharedPreferences = getSharedPreferences("My_preferences", Context.MODE_PRIVATE);
    }

    public void startRegistration(View view) {

        Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }
}
