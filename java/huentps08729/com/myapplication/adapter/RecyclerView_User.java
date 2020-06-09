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
import huentps08729.com.myapplication.model.TheLoai;
import huentps08729.com.myapplication.model.User;

public class RecyclerView_User extends RecyclerView.Adapter<RecyclerView_User.MyHolder> {


    Context context;
    List<User> users= new ArrayList<>();

    public RecyclerView_User(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {

        String user= users.get(i).getUserName();
        String phone= users.get(i).getPhone();

        holder.txtuser1.setText(user);
        holder.txtphone.setText(phone);


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView imgicon;
        TextView txtuser1,txtphone;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgicon= itemView.findViewById(R.id.imgicon);
            txtuser1= itemView.findViewById(R.id.txtuser1);
            txtphone= itemView.findViewById(R.id.txtPhone1);
        }
    }
}
