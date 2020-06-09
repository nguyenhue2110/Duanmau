package huentps08729.com.myapplication.dao;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.model.User;

public class UserDao {
    public static String UserName="userName";
    public static User cruser;
    public static String Pass="passWord";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferences;
    private List<User>users= new ArrayList<>();


    public interface data{
        void  dataload(List<User>users,List<String>key);
        void DataisInserted();
        void DataIsUpdated();
        void DataIsDelete();
    }
    public UserDao() {
        mDatabase= FirebaseDatabase.getInstance();
        mReferences=mDatabase.getReference("User");
    }

    public void readUser(final UserDao.data dataStatus){
        mReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                List<String> keys= new ArrayList<>();
                for (DataSnapshot keyNode:dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    User user= keyNode.getValue(User.class);
                    users.add(user);
                }
                dataStatus.dataload(users,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
