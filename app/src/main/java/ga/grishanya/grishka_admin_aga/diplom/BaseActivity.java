package ga.grishanya.grishka_admin_aga.diplom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaseActivity extends AppCompatActivity {

    RecyclerView ticketsList;
    TicketsListAdapter mTicketsListAdapter;

    //Первое меню
    Button sendTicket;
    EditText nameOfTicket;
    EditText subjectOfTicket;
    EditText discriptionOfTicket;

    static String Key;//ToDo убрать этот дурацкий костыль
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showInputMyticket();
                    mTextMessage.setText(R.string.title_home);
                    ticketsList.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    hideInputMyTicket();
                    ticketsList.setVisibility(View.VISIBLE);
                    getUserTicket();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    ticketsList.setVisibility(View.GONE);
                    hideInputMyTicket();
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
        Key=loadRes("Key");

        sendTicket=(Button) findViewById(R.id.sendTicket);
        nameOfTicket=(EditText) findViewById(R.id.NameOfTicket);
        subjectOfTicket=(EditText) findViewById(R.id.subjectOfTicket);
        discriptionOfTicket=(EditText) findViewById(R.id.discriptionOfTicket);


        //список тикетов
        ticketsList=(RecyclerView) findViewById(R.id.ticketsList);

        mTicketsListAdapter=new TicketsListAdapter(new ArrayList<TicketResponse>(0),new TicketsListAdapter.PostTicketListener(){

            @Override
            public void onPostLongClick(String dateOfCreation, String owner, String priority) {

                Toast.makeText(BaseActivity.this,"Post created time is "+dateOfCreation+"\n"+"Post Owner "+owner+"\n"+"Post priority "+priority,Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        ticketsList.setLayoutManager(layoutManager);
        ticketsList.setAdapter(mTicketsListAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        ticketsList.addItemDecoration(itemDecoration);

    }

    public void getUserTicket(){
       RetrofitClient.getGrishanyaApi().getTickets("Token "+Key).enqueue(new Callback<List<TicketResponse>>() {//ToDo убрать этот дурацкий костыль
           @Override
           public void onResponse(Call<List<TicketResponse>> call, Response<List<TicketResponse>> response) {
               if(response.isSuccessful()){

                   Log.i("TicketsList","download");
                   mTicketsListAdapter.updateTickets(response.body());
                   try{if(response.body().isEmpty()){mTextMessage.setText("Now your tickets list is empty");}}
                   catch (NullPointerException e){}
               }
               Log.i("TicketsList",response.code()+"");
           }

           @Override
           public void onFailure(Call<List<TicketResponse>> call, Throwable t) {
               Log.i("TicketsList","Failed");
           }
       });
    }

    public void ticketSend (String ticketTitle,String ticketSubject, String ticketDiscription){

        TicketRequest ticketRequest = new TicketRequest(ticketTitle+"",ticketSubject+"",ticketDiscription+"");
        RetrofitClient.getGrishanyaApi().sendTicket("Token "+Key,ticketRequest).enqueue(new Callback<TicketSendResponse>() {
            @Override
            public void onResponse(Call<TicketSendResponse> call, Response<TicketSendResponse> response) {
                Log.i("TicketSend", response.code()+"");
                if(response.isSuccessful()){mTextMessage.setText("Ticket was sent succesful!");
                nameOfTicket.setText("");
                subjectOfTicket.setText("");
                discriptionOfTicket.setText("");
                }
            }

            @Override
            public void onFailure(Call<TicketSendResponse> call, Throwable t) {
                Log.i("TicketSend","failed");
                mTextMessage.setText("Error!");
            }

        });
    }

    @Override//Меню в правом верхнем углу
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    public void hideInputMyTicket(){
        sendTicket.setVisibility(View.GONE);
        nameOfTicket.setVisibility(View.GONE);
        discriptionOfTicket.setVisibility(View.GONE);
        subjectOfTicket.setVisibility(View.GONE);
    }

    public void showInputMyticket(){
        sendTicket.setVisibility(View.VISIBLE);
        nameOfTicket.setVisibility(View.VISIBLE);
        discriptionOfTicket.setVisibility(View.VISIBLE);
        subjectOfTicket.setVisibility(View.VISIBLE);
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

    public void sendTiketButtonClick(View view) {
        ticketSend(nameOfTicket.getText()+"",subjectOfTicket.getText()+"",discriptionOfTicket.getText()+"");
    }
}
