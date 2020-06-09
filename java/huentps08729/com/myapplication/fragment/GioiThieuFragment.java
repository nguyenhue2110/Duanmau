package huentps08729.com.myapplication.fragment;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.adapter.RecyclerView_theloai;
import huentps08729.com.myapplication.dao.FirebasedatabaseHelper;
import huentps08729.com.myapplication.model.TheLoai;

/**
 * A simple {@link Fragment} subclass.
 */
public class GioiThieuFragment extends Fragment {

private RecyclerView mrecyclerView;


    EditText edttendl, edtmotadl, edtvitridl;
    Button btnuploadd, btnChose,btnadddl,btncanceldl;
    ImageView imgdl;
    int REQUEST_CODE_FOLDER=432;
    Uri mFilePathUri;

    StorageReference mStorageReference;
    DatabaseReference mdatabaseReference;
    ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sach, container, false);


        mrecyclerView= view.findViewById(R.id.rycle2);

        new FirebasedatabaseHelper().readTheloai(new FirebasedatabaseHelper.DataStatus() {
            @Override
            public void Dataisloadeded(List<TheLoai> theLoais, List<String> key) {
                new RecyclerView_theloai().settheloai(mrecyclerView,getActivity(),theLoais,key);
            }

            @Override
            public void DataisInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDelete() {

            }
        });


        return view;
    }







}
