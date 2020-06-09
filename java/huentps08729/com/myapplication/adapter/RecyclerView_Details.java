package huentps08729.com.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.Update_bill_Activity;
import huentps08729.com.myapplication.Update_details_Activity;
import huentps08729.com.myapplication.dao.DetailsBillDao;
import huentps08729.com.myapplication.model.HoaDonCT;

public class RecyclerView_Details {

    private Context mcontext;
    private DetailsAdapter detailsAdapter;
    public void sethd(RecyclerView recyclerView,Context context,List<HoaDonCT>hdlist,List<String>keys){
        mcontext= context;
        detailsAdapter= new DetailsAdapter(hdlist,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(detailsAdapter);
    }

    class DetailsItemView extends RecyclerView.ViewHolder{
TextView txtdemhd,txtdedebook,txtdesl,txtdegia,txtdetotal,txtdatea;
String key;
        public DetailsItemView(ViewGroup parent) {
            super(LayoutInflater.from(mcontext).inflate(R.layout.item_details,parent,false));

            txtdemhd= itemView.findViewById(R.id.txtdemhd);
            txtdegia= itemView.findViewById(R.id.txtdegia);
            txtdedebook= itemView.findViewById(R.id.txtdedebook);
            txtdesl= itemView.findViewById(R.id.txtdesl);
            txtdetotal= itemView.findViewById(R.id.txtdetotal);
            txtdatea=itemView.findViewById(R.id.txtdatea);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence[] items = {"Update", "Delete"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mcontext);
                    dialog.setTitle("Choose an action");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i == 0) {

                                Intent intent= new Intent(mcontext, Update_details_Activity.class);
                                intent.putExtra("key",key);
                                intent.putExtra("maHd",txtdemhd.getText().toString());
                                intent.putExtra("maS",txtdedebook.getText().toString());
                                intent.putExtra("ngayb",txtdatea.getText().toString());
                                mcontext.startActivity(intent);


                            }
                            if (i == 1) {
                                AlertDialog.Builder dialogdelete= new AlertDialog.Builder(mcontext);
                                dialogdelete.setTitle("Xóa");
                                dialogdelete.setMessage("bạn có muốn xóa không");
                                dialogdelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    new DetailsBillDao().delete(key, new DetailsBillDao.Data() {
                                        @Override
                                        public void upLoaded(List<HoaDonCT> hoaDonCTList, List<String> keys) {

                                        }

                                        @Override
                                        public void insert() {

                                        }

                                        @Override
                                        public void update() {

                                        }

                                        @Override
                                        public void delete() {
                                            Toast.makeText(mcontext, "đã xóa", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    }
                                });

                                dialogdelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                dialogdelete.show();

                            }

                        }
                    });
                    dialog.show();

                }
            });

        }

        public void bind(HoaDonCT hd, String key){
            txtdemhd.setText(hd.getDetailsid()+"");
            txtdedebook.setText(hd.getIdbook());
            txtdatea.setText(hd.getDate());
            txtdesl.setText(hd.getQuantity()+"");
            txtdegia.setText(hd.getPrice()+" .VNĐ");
            txtdetotal.setText(hd.getTotal()+" .VNĐ");
            this.key =  key;


        }
    }
class DetailsAdapter extends RecyclerView.Adapter<DetailsItemView>{
    public List<HoaDonCT>hoaDonCTS;
    private List<String> mkeys;

    public DetailsAdapter(List<HoaDonCT> hoaDonCTS, List<String> mkeys) {
        this.hoaDonCTS = hoaDonCTS;
        this.mkeys = mkeys;
    }

    @NonNull
    @Override
    public DetailsItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailsItemView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsItemView holder, int position) {
        holder.bind(hoaDonCTS.get(position),mkeys.get(position));
    }

    @Override
    public int getItemCount() {
        return hoaDonCTS.size();
    }
}


}
