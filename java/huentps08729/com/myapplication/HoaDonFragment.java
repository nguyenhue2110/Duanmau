package huentps08729.com.myapplication;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import huentps08729.com.myapplication.fragment.BillDetailsFragment;
import huentps08729.com.myapplication.fragment.BillFragment;
import huentps08729.com.myapplication.fragment.SachFragment;
import huentps08729.com.myapplication.fragment.TheLoaiFragment;


public class HoaDonFragment extends Fragment {
    BottomNavigationView bottomMenu;
    FrameLayout frmhd;
    BillFragment billFragment;
    BillDetailsFragment billDetailsFragment;
BottomNavigationBehavior bottomNavigationBehavior;
    public HoaDonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hoa_don, container, false);

        bottomMenu=(BottomNavigationView)view.findViewById(R.id.bottomngvb);
        bottomMenu.inflateMenu(R.menu.menu_bill);
        bottomMenu.setItemIconTintList(null);
        frmhd = view.findViewById(R.id.frmhd);
        getFragmentManager().beginTransaction().replace(R.id.frmhd,new BillFragment()).commit();
        billFragment= new BillFragment();
        billDetailsFragment = new BillDetailsFragment();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottommenu= view.findViewById(R.id.bottomngvb);
        bottommenu.setOnNavigationItemSelectedListener(navListener);



    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectFragment=null;
            switch (menuItem.getItemId()){
                case R.id.itembill:
                    selectFragment= new BillFragment();
                    break;
                case R.id.itemdetail:
                    selectFragment= new BillDetailsFragment();
                    break;
            }

            getFragmentManager().beginTransaction().replace(R.id.frmhd,selectFragment).commit();
            return true;
        }
    };


}
