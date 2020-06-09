package huentps08729.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.dao.DetailsBillDao;
import huentps08729.com.myapplication.fragment.BillDetailsFragment;
import huentps08729.com.myapplication.model.HoaDonCT;
import huentps08729.com.myapplication.model.Sach;
import huentps08729.com.myapplication.model.User;

public class HoaDonCTActivity extends AppCompatActivity {
EditText edtmahd,edtsoluong,edtdatea;
Spinner edtbmasach1;
ImageView imgback;
Button btnTinh,btnThanhToan;
TextView txtGiahd,txttotal;
    int gia;
    String ten;
    DatabaseReference table_book;
    FirebaseDatabase database;
    DatabaseReference Bill_cart;
    String mas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_ct);

        edtbmasach1= findViewById(R.id.edtbmasach1);
        edtmahd= findViewById(R.id.edtmahd);
        edtsoluong= findViewById(R.id.edtsoluong);
        btnThanhToan= findViewById(R.id.btnThanhToan);
        btnTinh= findViewById(R.id.btnTinh);
        imgback= findViewById(R.id.imgback);
        txtGiahd= findViewById(R.id.txtGiahd);
        txttotal= findViewById(R.id.txttotal);
        edtdatea= findViewById(R.id.edtdatea);
        String mahd= getIntent().getStringExtra("mahd");
        final String ngay= getIntent().getStringExtra("ngay");
        edtmahd.setText(mahd);
        edtdatea.setText(ngay);
        database = FirebaseDatabase.getInstance();
        table_book=database.getReference();

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


            btnThanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        HoaDonCT hd= new HoaDonCT();
                        String mas1= edtbmasach1.getSelectedItem().toString();
                        hd.setDetailsid(edtmahd.getText().toString());
                        hd.setIdbook(mas1);
                        hd.setDate(ngay);
                        hd.setQuantity(Integer.parseInt(edtsoluong.getText().toString()));

                        hd.setPrice(Integer.parseInt(txtGiahd.getText().toString()));
                        hd.setTotal(Integer.parseInt(txttotal.getText().toString()));
                        new DetailsBillDao().addhd(hd, new DetailsBillDao.Data() {
                            @Override
                            public void upLoaded(List<HoaDonCT> hoaDonCTList, List<String> keys) {

                            }

                            @Override
                            public void insert() {
                                Toast.makeText(HoaDonCTActivity.this, "tc", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void update() {

                            }

                            @Override
                            public void delete() {

                            }
                        });

                    }catch (NumberFormatException ex){

                    }


                }
            });
        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           showg();

            }
            public void showg(){
               final String mas=edtbmasach1.getSelectedItem().toString();
                table_book.child("Books").orderByChild("bookid").equalTo(mas).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Sach s= dataSnapshot.getChildren().iterator().next().getValue(Sach.class);
                        int gia= s.getGia();
                        txtGiahd.setText(gia+"");
                        Toast.makeText(HoaDonCTActivity.this, ""+mas, Toast.LENGTH_SHORT).show();
                        if(edtsoluong.getText().toString().length()==0){
                            Toast.makeText(HoaDonCTActivity.this, "nhập số lượng", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                int soluong= Integer.parseInt(edtsoluong.getText().toString());
                                int total= soluong * gia;
                                txttotal.setText(""+ total);
                            }catch (NumberFormatException ex){

                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }



                });


            }




        });



        table_book.child("Books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> booklist = new ArrayList<String>();


                for (DataSnapshot addressSnapshot : dataSnapshot.getChildren()) {
                    String masach = addressSnapshot.child("bookid").getValue(String.class);
                    if (masach != null) {
                        booklist.add(masach);
                    }
                }

                edtbmasach1=findViewById(R.id.edtbmasach1);
                ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(HoaDonCTActivity.this, android.R.layout.simple_spinner_item, booklist);
                addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                edtbmasach1.setAdapter(addressAdapter);
                String mas= edtbmasach1.getSelectedItem().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }




}
