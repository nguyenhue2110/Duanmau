package huentps08729.com.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import huentps08729.com.myapplication.dao.UserDao;
import huentps08729.com.myapplication.model.User;

public class ProfileActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    ImageView imvavata,imgpr,imgmenu;
    FirebaseUser firebaseUser;
    TextView txtUser,txtPhone;
    UserDao userDao;
EditText edtPasc,edtNewPass,edtxnpass;
Button btnxn, btnhuyp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userDao= new UserDao();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("User");
        imvavata= findViewById(R.id.imvavata);
        txtUser= findViewById(R.id.txtUser);
        txtPhone=findViewById(R.id.txtPhone);
        imgpr= findViewById(R.id.imgpr);
        imgmenu= findViewById(R.id.imgmenu);
        imgpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangePass();
            }
        });
        Intent intent= getIntent();
        String tendn=intent.getStringExtra("tendn1");
        txtUser.setText(tendn);
        Query query=databaseReference.orderByChild("userName").equalTo(tendn);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String phone= " " +ds.child("phone").getValue();

                    txtPhone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void showChangePass() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_changepass);

        edtPasc = dialog.findViewById(R.id.edtPasc);
        edtNewPass = dialog.findViewById(R.id.edtNewPass);
        edtxnpass = dialog.findViewById(R.id.edtxnpass);
        btnxn = dialog.findViewById(R.id.btnxn);
        btnhuyp = dialog.findViewById(R.id.btnhuyp);
        btnhuyp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPasc.getText().toString().equals(UserDao.cruser.getPassWord())){
                    if(edtNewPass.getText().toString().equals(edtxnpass.getText().toString())){

                        Map<String,Object>passwordupdate= new HashMap<>();
                        passwordupdate.put("passWord",edtNewPass.getText().toString());
                        FirebaseDatabase database= FirebaseDatabase.getInstance();
                        final DatabaseReference table_user= database.getReference("User");
                        table_user.child(UserDao.cruser.getUserName()).updateChildren(passwordupdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ProfileActivity.this, "update tc", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }else {
                        Toast.makeText(ProfileActivity.this, "xách nhận mk k khớp", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(ProfileActivity.this, "sai pas cũ", Toast.LENGTH_SHORT).show();
                }

            }
        });



        int width = (int) (getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();


    }


}
