package ga.grishanya.grishka_admin_aga.diplom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaseActivity extends AppCompatActivity {

    RecyclerView ticketsList;
    TicketsListAdapter mTicketsListAdapter;

    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    ticketsList.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    ticketsList.setVisibility(View.VISIBLE);
                    getUserTicket();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    ticketsList.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //список тикетов
// ТРЕБУЕТСЯ ПЕРЕПИСАТь******
        ticketsList=(RecyclerView) findViewById(R.id.ticketsList);

        mTicketsListAdapter=new TicketsListAdapter(new ArrayList<TicketResponse>(0));
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        ticketsList.setLayoutManager(layoutManager);
        ticketsList.setAdapter(mTicketsListAdapter);
// ТРЕБУЕТСЯ ПЕРЕПИСАТь******
    }

    public void getUserTicket(){
       RetrofitClient.getGrishanyaApi().getTickets("Token "+RetrofitClient.getKeyToken()).enqueue(new Callback<List<TicketResponse>>() {
           @Override
           public void onResponse(Call<List<TicketResponse>> call, Response<List<TicketResponse>> response) {
               if(response.isSuccessful()){
                   Log.i("TicketsList","download");
                   mTicketsListAdapter=new TicketsListAdapter(response.body());
                   ticketsList.setAdapter(mTicketsListAdapter);
               }
               Log.i("TicketsList",response.code()+"");
           }

           @Override
           public void onFailure(Call<List<TicketResponse>> call, Throwable t) {
               Log.i("TicketsList","Failed");
           }
       });
    }



    @Override//Меню в правом верхнем углу
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override//Меню в правом верхнем углу
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.BaseActivity_logout:
                saveText("","rememberUser");
                finish();//ToDo: Нужно чтобы даже если пользователь регистрировался, его все равно возвращало на Логина
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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


    @Override//завершение работы приложения при нажатии клавиши назад
    public void onBackPressed() {
        finishAffinity();
    }
}
