package huentps08729.com.myapplication.dao;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.model.TheLoai;

public class FirebasedatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferences;
    private List<TheLoai>theloais= new ArrayList<>();


    public interface DataStatus{
        void  Dataisloadeded(List<TheLoai>theLoais,List<String>key);
        void DataisInserted();
        void DataIsUpdated();
        void DataIsDelete();
    }
    public FirebasedatabaseHelper() {
        mDatabase= FirebaseDatabase.getInstance();
        mReferences=mDatabase.getReference("Categories");
    }

    public void readTheloai(final DataStatus dataStatus){
        mReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                theloais.clear();
                List<String>keys= new ArrayList<>();
                for (DataSnapshot keyNode:dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    TheLoai theLoai= keyNode.getValue(TheLoai.class);
                    theloais.add(theLoai);
                }
                dataStatus.Dataisloadeded(theloais,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void addTheLoai(TheLoai theLoai,final DataStatus dataStatus){

        String key= mReferences.push().getKey();
        mReferences.child(key).setValue(theLoai)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataisInserted();
                    }
                });
    }

    public void updateTheloai(String key,TheLoai theLoai,final DataStatus dataStatus){
        mReferences.child(key).setValue(theLoai).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void delete(String key,final DataStatus dataStatus){
        mReferences.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDelete();
            }
        });
    }
}
