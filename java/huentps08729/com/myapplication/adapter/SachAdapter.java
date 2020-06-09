package huentps08729.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.ItemClickListener;
import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.model.Sach;
import huentps08729.com.myapplication.model.TheLoai;

public class SachAdapter extends RecyclerView.ViewHolder implements
        View.OnClickListener,View.OnLongClickListener{
    private ItemClickListener itemClickListener;
    public SachAdapter(@NonNull View itemView) {
        super(itemView);


        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListenner.onItemClick(view,getAdapterPosition());

            }
        });


        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListenner.onItemLongClick(view,getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onclick(view,getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener= itemClickListener;
    }

    @Override
    public boolean onLongClick(View view) {

        itemClickListener.onclick(view,getAdapterPosition(),true);
        return true;
    }

    public void setOnClickListener(theloaiadapter.ClickListener clickListener) {
        mClickListenner= clickListener;

    }

    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int postion);
    }
    private theloaiadapter.ClickListener mClickListenner;


    public void setDetails(Context context, String imgSach, String tenSach, String tenTL, String tenNXB, String tenTG, int gia,String baove ){
        TextView txttentl1= itemView.findViewById(R.id.txttentl1);
        TextView txttensach1= itemView.findViewById(R.id.txttensach1);
        TextView txtNXB= itemView.findViewById(R.id.txtNXB);
        TextView txtTG= itemView.findViewById(R.id.txtTG);
        TextView txtGia= itemView.findViewById(R.id.txtGia);
        TextView txtbv= itemView.findViewById(R.id.txtbv);
        ImageView imgsach1= itemView.findViewById(R.id.imgsach1);


        txttentl1.setText(tenTL);
        txtGia.setText(String.valueOf(gia));
        txtTG.setText(tenTG);
        txtNXB.setText(tenNXB);
        txttensach1.setText(tenSach);
        txtbv.setText(baove);
        Picasso.get().load(imgSach).into(imgsach1);




    }
    public static class RecyclerAdapter extends RecyclerView.Adapter<SachAdapter>{
        private Context context;
        private List<Sach> sachList= new ArrayList<>();

        public RecyclerAdapter(Context context, List<Sach> sachList) {
            this.context = context;

        }

        @NonNull
        @Override
        public SachAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_sach,parent,false);

            return new SachAdapter(itemView);
        }

        @Override
        public void onBindViewHolder(SachAdapter holder, int i) {

        }

        @Override
        public int getItemCount() {
            return sachList.size();
        }




    }



}
