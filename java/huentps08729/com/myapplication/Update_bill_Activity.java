package huentps08729.com.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import huentps08729.com.myapplication.dao.Billdao;
import huentps08729.com.myapplication.model.Bill;

public class Update_bill_Activity extends AppCompatActivity  {
    EditText edtMahdup,edtNgaybup;
    Button btnThembup,btnHuybup;
    private String key,mahd,ngay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bill_);
        edtMahdup = findViewById(R.id.edtMahdup);
        edtNgaybup = findViewById(R.id.edtNgaybup);
        btnHuybup = findViewById(R.id.btnHuybup);
        btnThembup = findViewById(R.id.btnThembup);

         mahd=getIntent().getStringExtra("mahd");
          ngay= getIntent().getStringExtra("ngayb");
        key=getIntent().getStringExtra("key");
        edtMahdup.setText(mahd);
        edtNgaybup.setText(ngay);
        btnHuybup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edtNgaybup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });

        btnThembup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bill bill= new Bill(key,mahd,ngay);
                bill.setDate(edtNgaybup.getText().toString());
                bill.setBillName(edtMahdup.getText().toString());
                new Billdao().update(key, bill, new Billdao.DataStatus() {
                    @Override
                    public void upLoaded(List<Bill> billList, List<String> keys) {

                    }

                    @Override
                    public void insert() {

                    }

                    @Override
                    public void update() {
                        Toast.makeText(Update_bill_Activity.this, "update thanh cong", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void delete() {

                    }
                });
            }
        });




    }

    private void ChonNgay(){
        final Calendar calendar= Calendar.getInstance();
        int ngay= calendar.get(Calendar.DATE);
        int thang= calendar.get(Calendar.MONTH);
        int nam= calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                edtNgaybup.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
}
