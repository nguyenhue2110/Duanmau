package huentps08729.com.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import huentps08729.com.myapplication.dao.DetailsBillDao;
import huentps08729.com.myapplication.model.Bill;
import huentps08729.com.myapplication.model.HoaDonCT;
import huentps08729.com.myapplication.model.Sach;

public class Update_details_Activity extends AppCompatActivity {
EditText edtSoluongde;
TextView txtMahdde,txtMaSachde,txtgiade,txttongde,txtdatebu;
ImageView imgbackde;
Button btnADDde,btnSavede;
    DatabaseReference table_book;
    FirebaseDatabase database;
    private String key,maHd,mas,date;
    int total, gia,soluong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details_);
        edtSoluongde= findViewById(R.id.edtSoluongde);
        txtMahdde= findViewById(R.id.txtMahdde);
        txtMaSachde= findViewById(R.id.txtMaSachde);
        txtgiade= findViewById(R.id.txtgiade);
        txttongde= findViewById(R.id.txttongde);
        txtdatebu=findViewById(R.id.txtdatebu);
        btnADDde= findViewById(R.id.btnADDde);
        btnSavede= findViewById(R.id.btnSavede);
        database = FirebaseDatabase.getInstance();
        table_book=database.getReference();
        maHd=getIntent().getStringExtra("maHd");
        mas= getIntent().getStringExtra("maS");
        key=getIntent().getStringExtra("key");
        date=getIntent().getStringExtra("ngayb");
        txtMaSachde.setText(mas);
        txtMahdde.setText(maHd);
        txtdatebu.setText(date);

        btnADDde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showgdeta();
            }
        });

        btnSavede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                   HoaDonCT hdct= new HoaDonCT(maHd,mas,date,soluong,gia,total);
                   hdct.setDetailsid(txtMahdde.getText().toString());
                   hdct.setIdbook(txtMaSachde.getText().toString());
                   hdct.setDate(txtdatebu.getText().toString());
                   hdct.setQuantity(Integer.parseInt(edtSoluongde.getText().toString()));
                   hdct.setPrice(Integer.parseInt(txtgiade.getText().toString()));
                   hdct.setTotal(Integer.parseInt(txttongde.getText().toString()));

                   new DetailsBillDao().update(key, hdct, new DetailsBillDao.Data() {
                       @Override
                       public void upLoaded(List<HoaDonCT> hoaDonCTList, List<String> keys) {

                       }

                       @Override
                       public void insert() {

                       }

                       @Override
                       public void update() {
                           Toast.makeText(Update_details_Activity.this, "update thanh cong", Toast.LENGTH_SHORT).show();
                           finish();
                       }

                       @Override
                       public void delete() {

                       }
                   });
               }catch (NumberFormatException ex){

               }

            }
        });
    }



    public void showgdeta(){
        String masac=txtMaSachde.getText().toString();
        table_book.child("Books").orderByChild("bookid").equalTo(masac).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Sach s= dataSnapshot.getChildren().iterator().next().getValue(Sach.class);
                int gia= s.getGia();
                txtgiade.setText(gia+"");
                if(edtSoluongde.getText().toString().length()==0){
                    Toast.makeText(Update_details_Activity.this, "nhập số lượng", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        int soluong= Integer.parseInt(edtSoluongde.getText().toString());
                        int total= soluong * gia;
                        txttongde.setText(""+ total);
                    }catch (NumberFormatException ex){

                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
            }



        });


    }
}
