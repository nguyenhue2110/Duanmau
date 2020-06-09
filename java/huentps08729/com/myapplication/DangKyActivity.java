package huentps08729.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import huentps08729.com.myapplication.model.User;

public class DangKyActivity extends AppCompatActivity {
    EditText edtFullName, edtPhone,edtPassword,edtConfirm;
    Button btndky,btncancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dang_ky);
        edtFullName= findViewById(R.id.edtFullName);
        edtPhone= findViewById(R.id.edtpPhone);
        edtPassword= findViewById(R.id.edtPassword);
        edtConfirm= findViewById(R.id.edtConfirm);
        btndky= findViewById(R.id.btndky);
        btncancel= findViewById(R.id.btncancel);

        FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user= database.getReference("User");
            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        btndky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog= new ProgressDialog(DangKyActivity.this);
                mDialog.setMessage("Please waiting");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(edtFullName.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(DangKyActivity.this, "da tồn tai", Toast.LENGTH_SHORT).show();
                        } else {
                            if(edtFullName.getText().toString().isEmpty()|| edtPhone.getText().toString().isEmpty()||
                                    edtPassword.getText().toString().isEmpty()||edtConfirm.getText().toString().isEmpty()){
                                mDialog.dismiss();
                                Toast.makeText(DangKyActivity.this, "khong bo trong", Toast.LENGTH_SHORT).show();
                            }else{
                                mDialog.dismiss();
                                String Tentk= edtFullName.getText().toString();
                                String Phone= edtPhone.getText().toString();
                                String Password= edtPassword.getText().toString();
                                String Confirm= edtConfirm.getText().toString();
                                User user= new User(Tentk,Phone,Password,Confirm);
                                table_user.child(edtFullName.getText().toString()).setValue(user);

                                Toast.makeText(DangKyActivity.this, "dkytc", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(DangKyActivity.this,DangNhapActivity.class);
                                startActivity(intent);

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
