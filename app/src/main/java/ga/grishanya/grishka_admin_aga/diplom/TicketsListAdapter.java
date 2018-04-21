package ga.grishanya.grishka_admin_aga.diplom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import retrofit2.http.POST;

public class TicketsListAdapter extends RecyclerView.Adapter<TicketsListAdapter.ViewHolder>{

    private  List <TicketResponse>mTicketResponse;
    private Context mContext;//то объект, который предоставляет доступ к базовым функциям приложения: доступ к ресурсам, к файловой системе, вызов активности и т.д.
    private PostTicketListener mPostItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        public TextView titleTicket;
        public TextView ownerTicket;
        public TextView dataTicket;
        PostTicketListener mPostItemListener;

        public ViewHolder(View itemView, PostTicketListener postTicketListener){//Реализует обязательный паттерн вью холдера
            super(itemView);
            titleTicket=(TextView) itemView.findViewById(R.id.tikets_name);
            ownerTicket=(TextView) itemView.findViewById(R.id.tickets_owner);
            dataTicket=(TextView) itemView.findViewById(R.id.tikets_data_of_creation);

            this.mPostItemListener=postTicketListener;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view){

           TicketResponse ticketResponse=getTicket(getAdapterPosition());
           this.mPostItemListener.onPostLongClick(ticketResponse.getCreatedAt(),ticketResponse.getOwner(),ticketResponse.getPriority());

            return true;
        }


    }

    TicketsListAdapter (List<TicketResponse> tikets,PostTicketListener postTicketListener){
        this.mTicketResponse = tikets;
        mPostItemListener=postTicketListener;
    }

    @Override
    public int getItemCount() {
        return mTicketResponse.size();
    }

    @Override
    public TicketsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.users_tickets, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView,this.mPostItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TicketsListAdapter.ViewHolder holder, int position) {

        TicketResponse ticketResponse = mTicketResponse.get(position);
        TextView textView = holder.titleTicket;
        TextView ticOwner = holder.ownerTicket;
        TextView ticData = holder.dataTicket;
        textView.setText("Title: "+ticketResponse.getTitle());
        ticOwner.setText("Owner: "+ticketResponse.getOwner());
        ticData.setText("Created at: "+GetData.getData(ticketResponse.getCreatedAt()));
    }

    public void updateTickets(List<TicketResponse> tikets){
        mTicketResponse=tikets;
        notifyDataSetChanged();
    }

    private TicketResponse getTicket(int adapterPosition){
        return mTicketResponse.get(adapterPosition);
    }

    public interface PostTicketListener {
        void onPostLongClick(String dateOfCreation, String owner, String priority);
    }

}
