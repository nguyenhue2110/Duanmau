package huentps08729.com.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.ItemClickListener;
import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.model.TheLoai;

public class theloaiadapter extends RecyclerView.ViewHolder
      {
    private ClickListener mClickListenner;
   // private ItemClickListener itemClickListener;
    public theloaiadapter(@NonNull View itemView) {
        super(itemView);
//        itemView.setOnLongClickListener(this);
//        itemView.setOnClickListener(this);
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

//    @Override
//    public void onClick(View view) {
//        itemClickListener.onclick(view,getAdapterPosition(),false);
//
//    }
//
//    public void setItemClickListener(ItemClickListener itemClickListener){
//        this.itemClickListener= itemClickListener;
//    }
//
//    @Override
//    public boolean onLongClick(View view) {
//
//        itemClickListener.onclick(view,getAdapterPosition(),true);
//        return true;
//    }

    public void setOnClickListener(theloaiadapter.ClickListener clickListener) {
        mClickListenner= clickListener;

    }

    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int postion);
    }



    public void setDetails(Context context,String Name, String Image,String MoTa,int ViTri ){
        TextView txtTenLoai= itemView.findViewById(R.id.txtTenLoai);
        ImageView imgh1= itemView.findViewById(R.id.imgh1);
        TextView txtMota= itemView.findViewById(R.id.txtMota);
        TextView txtVitri= itemView.findViewById(R.id.txtViTri);

        txtTenLoai.setText(Name);
        txtVitri.setText(String.valueOf(ViTri));
        txtMota.setText(MoTa);
        Picasso.get().load(Image).into(imgh1);




    }
    public static class RecyclerAdapter extends RecyclerView.Adapter<theloaiadapter>{
        private Context context;
        private List<TheLoai> theLoaiList= new ArrayList<>();

        public RecyclerAdapter(Context context, List<TheLoai> theLoais) {
            this.context = context;

        }

        @NonNull
        @Override
        public theloaiadapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_theloai,parent,false);

            return new theloaiadapter(itemView);
        }

        @Override
        public void onBindViewHolder(theloaiadapter holder, int i) {

        }

        @Override
        public int getItemCount() {
            return theLoaiList.size();
        }




    }




}
