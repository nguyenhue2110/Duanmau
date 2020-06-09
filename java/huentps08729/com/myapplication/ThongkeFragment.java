package huentps08729.com.myapplication;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import huentps08729.com.myapplication.model.Bill;
import huentps08729.com.myapplication.model.Sach;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThongkeFragment extends Fragment {

    DatabaseReference tabe_bill;
    FirebaseDatabase database;
    DatabaseReference detailbill;
    TextView txttk1, txtdtt,txttheonama;
    String ngay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thongke, container, false);

        database = FirebaseDatabase.getInstance();
        tabe_bill = database.getReference();

        detailbill = database.getReference();
        txttk1 = view.findViewById(R.id.txttk1);
        txtdtt = view.findViewById(R.id.txtdtt);
        txttheonama=view.findViewById(R.id.txttheonama);

        tk();
        tktn();
        tktthang();
        return view;

    }


    public void tktthang(){


        String ngbd="1/10/2019";
        String nkt="31/10/2019";

        detailbill.child("Details").orderByChild("date").startAt(ngbd).endAt(nkt).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) d.getValue();
                    Object th = map.get("total");
                    int tvalue = Integer.parseInt(String.valueOf(th));
                    sum += tvalue;
                    txtdtt.setText( sum+ " .VNĐ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void tk() {

        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd hhmmss");
        dateFormatter.setLenient(false);
        Date today = new Date();
        String s = dateFormatter.format(today);
        String ngay = String.valueOf(s.substring(6, 9));

        detailbill.child("Details").orderByChild("date").startAt(ngay).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) d.getValue();
                    Object pr = map.get("total");
                    int pvalue = Integer.parseInt(String.valueOf(pr));
                    sum += pvalue;
                    txttk1.setText( sum+ " .VNĐ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
public void tktn(){
    DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd hhmmss");
    dateFormatter.setLenient(false);
    Date today = new Date();
    String s = dateFormatter.format(today);
    String nam = String.valueOf(s.substring(3, 4));

    detailbill.child("Details").orderByChild("date").endAt(nam).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            int sum = 0;
            for (DataSnapshot d : dataSnapshot.getChildren()) {
                Map<String, Object> map = (Map<String, Object>) d.getValue();
                Object n = map.get("total");
                int avalue = Integer.parseInt(String.valueOf(n));
                sum += avalue;
                txttheonama.setText( sum+ " .VNĐ");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });



}

}






