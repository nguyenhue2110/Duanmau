package huentps08729.com.myapplication.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.model.TheLoai;

public class RecyclerView_theloai {

    private Context mcontext;
    private TheLoaiAdapter mtheloaiAdapter;
    public void settheloai(RecyclerView recyclerView,Context context,List<TheLoai>theLoais,List<String>keys){
        mcontext=context;
        mtheloaiAdapter= new TheLoaiAdapter(theLoais,keys);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.setAdapter(mtheloaiAdapter);
    }

    class TheloaiItemView extends RecyclerView.ViewHolder{

       private TextView txtTenLoai2;
        private ImageView imgh2;
        private  TextView txtMota2;
        private  TextView txtVitri2;
        private ImageView imgmenu2;

        private String key;

        public TheloaiItemView(ViewGroup parent) {
            super(LayoutInflater.from(mcontext).
                    inflate(R.layout.item_tl,parent,false));

          txtTenLoai2= itemView.findViewById(R.id.txtTenLoai2);

          imgh2= itemView.findViewById(R.id.imgh2);


        }

        public void bind(TheLoai theLoai,String key){
           txtTenLoai2.setText(theLoai.getName());
//           txtVitri2.setText(String.valueOf(theLoai.getViTri()));
//           txtMota2.setText(theLoai.getMoTa());
           Picasso.get().load(theLoai.getImage()).into(imgh2);
           this.key=key;

       }
    }

    class TheLoaiAdapter extends RecyclerView.Adapter<TheloaiItemView>{
        private List<TheLoai>mtheloailist;
        private List<String>mkeys;
        private Context context;

        public TheLoaiAdapter(List<TheLoai>mtheloailist,List<String>mkeys){
            this.context= context;
            this.mtheloailist=mtheloailist;
            this.mkeys= mkeys;

        }

        @NonNull
        @Override
        public TheloaiItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_tl,parent,false);


            return new TheloaiItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TheloaiItemView holder, int position) {


            holder.bind(mtheloailist.get(position),mkeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mtheloailist.size();
        }
    }



}
