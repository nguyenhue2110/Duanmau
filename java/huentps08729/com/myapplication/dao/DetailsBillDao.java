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

import huentps08729.com.myapplication.model.HoaDonCT;

public class DetailsBillDao {

    private FirebaseDatabase database;
    private DatabaseReference detailbill;
    private List<HoaDonCT>hoaDonCTList= new ArrayList<>();

    public DetailsBillDao(FirebaseDatabase database, DatabaseReference detailbill, List<HoaDonCT> hoaDonCTList) {
        this.database = database;
        this.detailbill = detailbill;
        this.hoaDonCTList = hoaDonCTList;
    }
    public interface Data{
        void upLoaded(List<HoaDonCT>hoaDonCTList,List<String>keys);
        void insert();
        void update();
        void delete();

    }
    public DetailsBillDao(){
        database=FirebaseDatabase.getInstance();
        detailbill=database.getReference("Details");
    }


    public void readdetail(final Data dataStatus){
        detailbill.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hoaDonCTList.clear();
                List<String>keys= new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    HoaDonCT hd= keyNode.getValue(HoaDonCT.class);
                    hoaDonCTList.add(hd);
                }
                dataStatus.upLoaded(hoaDonCTList,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void addhd(HoaDonCT hd,final Data dataStatus){
        String key= detailbill.push().getKey();
        detailbill.child(key).setValue(hd).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.insert();
            }
        });
    }

    public void delete(String key,final Data datatus){
        detailbill.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                datatus.delete();
            }
        });

    }


    public void update(String key,HoaDonCT hd,final Data datatus){

        detailbill.child(key).setValue(hd).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                datatus.update();
            }
        });
    }
}
