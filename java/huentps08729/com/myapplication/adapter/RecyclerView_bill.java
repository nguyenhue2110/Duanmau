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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import huentps08729.com.myapplication.HoaDonCTActivity;
import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.Update_bill_Activity;
import huentps08729.com.myapplication.Update_theloai_Activity;
import huentps08729.com.myapplication.dao.Billdao;
import huentps08729.com.myapplication.model.Bill;

public class RecyclerView_bill {
    private Context mcontext;
    private Billadapter billadapter;


    public void setbill(RecyclerView recyclerView, Context context, List<Bill> billList, List<String> keys) {
        mcontext= context;
        billadapter = new Billadapter(billList,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(billadapter);
    }

    class BillItemView extends RecyclerView.ViewHolder{
        public String key;

        ImageView imgbill;
        List<Bill>bills;
        TextView txtMahd,txtNgayb;
        public BillItemView(ViewGroup parent) {
            super(LayoutInflater.from(mcontext).inflate(R.layout.item_bill,parent,false));
              txtMahd=itemView.findViewById(R.id.txtMabill);
              txtNgayb= itemView.findViewById(R.id.txtNgaybill);


              itemView.setOnLongClickListener(new View.OnLongClickListener() {
                  @Override
                  public boolean onLongClick(View view) {

                      CharSequence[] items = {"Update", "Delete"};
                      AlertDialog.Builder dialog = new AlertDialog.Builder(mcontext);
                      dialog.setTitle("Choose an action");
                      dialog.setItems(items, new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              if(i==0){
                                  Intent intent= new Intent(mcontext, Update_bill_Activity.class);
                                  intent.putExtra("key",key);
                                  intent.putExtra("mahd",txtMahd.getText().toString());
                                  intent.putExtra("ngayb",txtNgayb.getText().toString());

                                  mcontext.startActivity(intent);
                              }

                              if(i==1){

                                  AlertDialog.Builder dialogdelete= new AlertDialog.Builder(mcontext);
                                  dialogdelete.setTitle("Xóa");
                                  dialogdelete.setMessage("bạn có muốn xóa không");
                                  dialogdelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                          new Billdao().delete(key, new Billdao.DataStatus() {
                                              @Override
                                              public void upLoaded(List<Bill> billList, List<String> keys) {

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


                      return true;
                  }
              });

         itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent= new Intent(mcontext, HoaDonCTActivity.class);
                    intent.putExtra("mahd",txtMahd.getText().toString());
                    intent.putExtra("ngay",txtNgayb.getText().toString());
                mcontext.startActivity(intent);
                }
            });
        }

        public void bind(Bill bill,String key){

            txtMahd.setText(bill.getBillName());
            txtNgayb.setText(bill.getDate());
            this.key= key;
        }


    }

    class Billadapter extends RecyclerView.Adapter<BillItemView>{
        public List<Bill>mbills;
        public List<String>mkeys;


        public Billadapter(List<Bill> mbills, List<String> mkeys) {
            this.mbills = mbills;
            this.mkeys = mkeys;

        }

        @NonNull
        @Override
        public BillItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BillItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BillItemView holder, int position) {
        holder.bind(mbills.get(position),mkeys.get(position));


        }

        @Override
        public int getItemCount() {
            return mbills.size();
        }
    }



}
