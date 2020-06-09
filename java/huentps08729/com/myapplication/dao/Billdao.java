package huentps08729.com.myapplication.dao;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.model.Bill;

public class Billdao {
    DatabaseReference tabe_bill;
    FirebaseDatabase database;

private List<Bill>billList= new ArrayList<>();




    public interface DataStatus{
        void upLoaded(List<Bill>billList,List<String>keys);
        void insert();
        void update();
        void delete();

    }
    public Billdao(DatabaseReference tabe_bill, FirebaseDatabase database, List<Bill> billList) {
        this.tabe_bill = tabe_bill;
        this.database = database;
        this.billList = billList;
    }

    public Billdao() {
        database=FirebaseDatabase.getInstance();
        tabe_bill=database.getReference("Bill");

    }


public void readbill(final DataStatus dataStatus){
    tabe_bill.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                billList.clear();
                List<String>keys= new ArrayList<>();
                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Bill bill= keyNode.getValue(Bill.class);
                    billList.add(bill);
                }
                dataStatus.upLoaded(billList,keys);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}

public void addBill(Bill bill, final DataStatus dataStatus){
        String id= tabe_bill.push().getKey();

    tabe_bill.child(id).setValue(bill).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            dataStatus.insert();
            }
        });
}


public void delete(String key,final DataStatus dataStatus){

    tabe_bill.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            dataStatus.delete();
        }
    });
}

public void update(String key, Bill bill,final DataStatus dataStatus){

        String id= tabe_bill.push().getKey();
        tabe_bill.child(key).setValue(bill).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            dataStatus.update();
            }
        });
}
}
