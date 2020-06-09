package huentps08729.com.myapplication.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.adapter.RecyclerView_bill;
import huentps08729.com.myapplication.dao.Billdao;
import huentps08729.com.myapplication.model.Bill;


public class BillFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference tabe_bill;
    FirebaseDatabase database;
    FloatingActionButton fabbill;
        Billdao billdao;
    EditText edtMahd,edtNgayb;
    Button btnThemb,btnHuyb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_bill, container, false);
       fabbill=view.findViewById(R.id.fabbill);
        fabbill.setBackgroundTintList(getResources().getColorStateList(R.color.white));
       recyclerView= view.findViewById(R.id.recybill);
        database = FirebaseDatabase.getInstance();
        tabe_bill=database.getReference("Bill");
        fabbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogtl();
            }
        });
        new Billdao().readbill(new Billdao.DataStatus() {
            @Override
            public void upLoaded(List<Bill> billList, List<String> keys) {
                new RecyclerView_bill().setbill(recyclerView,getContext(),billList,keys);

            }

            @Override
            public void insert() {

            }

            @Override
            public void update() {

            }

            @Override
            public void delete() {

            }
        });
       return view;
    }


    public void dialogtl(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_bill);
        edtMahd = dialog.findViewById(R.id.edtMahd);
        edtNgayb = dialog.findViewById(R.id.edtNgayb);

        btnThemb = dialog.findViewById(R.id.btnThemb);
        btnHuyb = dialog.findViewById(R.id.btnHuyb);



        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        edtNgayb.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ChonNgaykt();
        }
    });
        btnHuyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnThemb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(edtMahd.getText().toString().isEmpty()||edtNgayb.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "không bỏ trống", Toast.LENGTH_SHORT).show();
        }else {

            String id= tabe_bill.push().getKey();
            String tenhd= edtMahd.getText().toString();
            String ngayhd= edtNgayb.getText().toString();
            Bill bill= new Bill(id,tenhd,ngayhd);
            bill.setBillName(edtMahd.getText().toString());
            bill.setDate(edtNgayb.getText().toString());

            new Billdao().addBill(bill, new Billdao.DataStatus() {
                @Override
                public void upLoaded(List<Bill> billList, List<String> keys) {

                }

                @Override
                public void insert() {
                    Toast.makeText(getContext(), "thêm tc", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

                @Override
                public void update() {

                }

                @Override
                public void delete() {

                }
            });
        }
            }
        });


    }
    private void ChonNgaykt(){
        final Calendar calendar= Calendar.getInstance();
        int ngay= calendar.get(Calendar.DATE);
        int thang= calendar.get(Calendar.MONTH);
        int nam= calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                edtNgayb.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

}
