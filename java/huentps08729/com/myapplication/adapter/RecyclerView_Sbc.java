package huentps08729.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.model.HoaDonCT;
import huentps08729.com.myapplication.model.User;

public class RecyclerView_Sbc extends RecyclerView.Adapter<RecyclerView_Sbc.holder> {
    Context context;
    List<HoaDonCT> hoaDonCTS= new ArrayList<>();

    public RecyclerView_Sbc(Context context, List<HoaDonCT> hoaDonCTS) {
        this.context = context;
        this.hoaDonCTS = hoaDonCTS;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_sbc,parent,false);
        return new holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        String mahd= hoaDonCTS.get(position).getDetailsid();
        String mas= hoaDonCTS.get(position).getIdbook();
        int soluong=hoaDonCTS.get(position).getQuantity();

        holder.txtdemhdsbc.setText(mahd);
        holder.txtdedebooksbc.setText(mas);
        holder.txtdeslsbc.setText(""+ soluong);
    }

    @Override
    public int getItemCount() {
        return hoaDonCTS.size();
    }
    public class holder extends RecyclerView.ViewHolder {

        TextView txtdemhdsbc,txtdedebooksbc,txtensbc,txtdeslsbc;
        public holder (@NonNull View itemView) {
            super(itemView);
            txtdedebooksbc= itemView.findViewById(R.id.txtdedebooksbc);

            txtdemhdsbc= itemView.findViewById(R.id.txtdemhdsbc);
            txtdeslsbc= itemView.findViewById(R.id.txtdeslsbc);

        }
    }
}
