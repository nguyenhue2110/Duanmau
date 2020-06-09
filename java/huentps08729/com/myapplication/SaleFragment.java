package huentps08729.com.myapplication;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import huentps08729.com.myapplication.adapter.RecyclerView_Sbc;
import huentps08729.com.myapplication.model.Bill;
import huentps08729.com.myapplication.model.HoaDonCT;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaleFragment extends Fragment {
    RecyclerView rcysale;
    DatabaseReference tabe_bill;
    FirebaseDatabase database;
    DatabaseReference detailbill;
        RecyclerView_Sbc adapter;
    Button btnsearch;
    EditText edtsearch;
List<HoaDonCT>hoaDonCTList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sale, container, false);
        database = FirebaseDatabase.getInstance();
        tabe_bill = database.getReference();
        detailbill = database.getReference();
        edtsearch= view.findViewById(R.id.edtsearch);
        btnsearch= view.findViewById(R.id.btnsearch);
        rcysale= view.findViewById(R.id.rcysale);
       load();
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbc();
                load();
            }
        });
        return view;

    }


    public void sbc(){
        detailbill.child("Details").orderByChild("quantity").limitToFirst(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String th=edtsearch.getText().toString();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    HoaDonCT hoaDonCT=ds.getValue(HoaDonCT.class);
                    String ngayd= hoaDonCT.getDate();
                    String thangd= ngayd.substring(3,5);

                    if(thangd.equals(th)){
                        hoaDonCTList.add(hoaDonCT);
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


public void load(){
    rcysale.setHasFixedSize(true);
    rcysale.setLayoutManager(new LinearLayoutManager(getContext()));

    hoaDonCTList= new ArrayList<>();
    adapter= new RecyclerView_Sbc(getContext(),hoaDonCTList);
    rcysale.setAdapter(adapter);
}
}
