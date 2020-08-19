package huentps08729.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import huentps08729.com.myapplication.dao.UserDao;
import huentps08729.com.myapplication.model.User;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
Button btnstart;
UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao= new UserDao();
        Paper.init(this);

        String usr= Paper.book().read(UserDao.UserName);
        String pwd= Paper.book().read(UserDao.Pass);

        if(usr!=null && pwd != null){
            if(!usr.isEmpty() && !pwd.isEmpty()){
                AllowAccess(usr,pwd);
            }
        }
        btnstart= findViewById(R.id.btnstart);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        });


//        Thread thread= new Thread(new Runnable() {
//            @Override
//            public void run() {
//               try {
//
//                   Thread.sleep(3000);
//               }catch (Exception e){
//
//               }finally {
//                Intent intent1= new Intent(MainActivity.this,DangNhapActivity.class);
//                startActivity(intent1);
//               }
//            }
//        });
//        thread.start();


//thaydoi

    }

    private void AllowAccess(final String userName, final String pwd) {
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");
        final ProgressDialog mDialog= new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Please waiting");
        mDialog.show();
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(userName.isEmpty()|| pwd.isEmpty()){
                    mDialog.dismiss();
                    Toast.makeText(MainActivity.this, "không bỏ trống", Toast.LENGTH_SHORT).show();
                }else {
                    //check user not exits
                    if(dataSnapshot.child(userName).exists()) {

                        mDialog.dismiss();
                        User user = dataSnapshot.child(userName).getValue(User.class);
                            user.setUserName(userName);
                        if (user.getPassWord().equals(pwd)){
                            Intent intent= new Intent(MainActivity.this, DangNhapActivity.class);
                            UserDao.cruser=user;
                            String us= user.getUserName();
                            String ps= user.getPassWord();
                            intent.putExtra("us",us);
                            intent.putExtra("ps",ps);
                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, "faild", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "User not exits", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
