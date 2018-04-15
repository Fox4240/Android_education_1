package ga.grishanya.grishka_admin_aga.diplom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TicketsListAdapter extends RecyclerView.Adapter<TicketsListAdapter.ViewHolder>{

    private  List <TicketResponse>mTicketResponse;
    private Context mContext;//то объект, который предоставляет доступ к базовым функциям приложения: доступ к ресурсам, к файловой системе, вызов активности и т.д.

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTicket;
        public ViewHolder(View itemView){
            super(itemView);
            titleTicket=(TextView) itemView.findViewById(android.R.id.text1);
        }

    }

    TicketsListAdapter (List<TicketResponse> tikets){
        this.mTicketResponse = tikets;
    }

    @Override
    public int getItemCount() {
        return mTicketResponse.size();
    }

    @Override
    public TicketsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TicketsListAdapter.ViewHolder holder, int position) {

        TicketResponse ticketResponse = mTicketResponse.get(position);
        TextView textView = holder.titleTicket;
        textView.setText(ticketResponse.getTitle());//ToDo: Убрать цифру в строке.
    }

    public void updateTickets(List<TicketResponse> tikets){
        mTicketResponse=tikets;
        notifyDataSetChanged();
    }

    private TicketResponse getTicket(int adapterPosition){
        return mTicketResponse.get(adapterPosition);
    }

}
