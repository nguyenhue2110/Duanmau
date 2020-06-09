package huentps08729.com.myapplication.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import huentps08729.com.myapplication.R;
import huentps08729.com.myapplication.adapter.RecyclerView_Details;
import huentps08729.com.myapplication.dao.DetailsBillDao;
import huentps08729.com.myapplication.model.HoaDonCT;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillDetailsFragment extends Fragment {
RecyclerView recyb;

    public BillDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bill_details, container, false);

        recyb= view.findViewById(R.id.rclde);



        new DetailsBillDao().readdetail(new DetailsBillDao.Data() {
            @Override
            public void upLoaded(List<HoaDonCT> hoaDonCTList, List<String> keys) {
                new RecyclerView_Details().sethd(recyb,getContext(),hoaDonCTList,keys);
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

}
