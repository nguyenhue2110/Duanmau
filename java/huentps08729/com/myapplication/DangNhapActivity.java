package huentps08729.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import huentps08729.com.myapplication.dao.UserDao;
import huentps08729.com.myapplication.model.User;
import io.paperdb.Paper;

public class DangNhapActivity extends AppCompatActivity {
Button btnregister,btnsign;
EditText edtUser,edtPass;
CheckBox chkpass;
UserDao userDao;
String us,ps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        btnregister= findViewById(R.id.btndangky);
        btnsign=findViewById(R.id.btndangnhap);
        edtUser= findViewById(R.id.edtUser);
        edtPass= findViewById(R.id.edtPass);
        chkpass= findViewById(R.id.chkmatKhau);

        userDao= new UserDao();

        Paper.init(this);
        String us= getIntent().getStringExtra("us");
        String ps= getIntent().getStringExtra("ps");
        edtUser.setText(us);
        edtPass.setText(ps);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(intent);
            }
        });

        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database= FirebaseDatabase.getInstance();
                final DatabaseReference table_user=database.getReference("User");

                final ProgressDialog mDialog= new ProgressDialog(DangNhapActivity.this);
                mDialog.setMessage("Please waiting");
                mDialog.show();

                if(chkpass.isChecked()){
                    Paper.book().write(UserDao.UserName,edtUser.getText().toString());
                    Paper.book().write(UserDao.Pass,edtPass.getText().toString());

                }

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(edtUser.getText().toString().isEmpty()|| edtPass.getText().toString().isEmpty()){
                            mDialog.dismiss();
                            Toast.makeText(DangNhapActivity.this, "không bỏ trống", Toast.LENGTH_SHORT).show();
                        }else {
                            //check user not exits
                            if(dataSnapshot.child(edtUser.getText().toString()).exists()) {

                                mDialog.dismiss();
                                User user = dataSnapshot.child(edtUser.getText().toString()).getValue(User.class);

                                if (user.getPassWord().equals(edtPass.getText().toString())) {
                                    Toast.makeText(DangNhapActivity.this, "tc", Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(DangNhapActivity.this, TrangChuActivity.class);
                                    intent.putExtra("tendn",edtUser.getText().toString());
                                    UserDao.cruser=user;
                                    startActivity(intent);
                                    table_user.removeEventListener(this);
                                } else {
                                    Toast.makeText(DangNhapActivity.this, "faild", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                mDialog.dismiss();
                                Toast.makeText(DangNhapActivity.this, "User not exits", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });




    }




}
