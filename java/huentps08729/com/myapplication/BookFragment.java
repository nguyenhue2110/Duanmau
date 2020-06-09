package huentps08729.com.myapplication;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import huentps08729.com.myapplication.fragment.GioiThieuFragment;
import huentps08729.com.myapplication.fragment.SachFragment;
import huentps08729.com.myapplication.fragment.TheLoaiFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {
    BottomNavigationView bottomMenu;
    FrameLayout frmbook;
    SachFragment sachFragment;
    TheLoaiFragment theLoaiFragment;


    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_book, container, false);

        bottomMenu=(BottomNavigationView)view.findViewById(R.id.bottomngv);
        bottomMenu.inflateMenu(R.menu.menu_bottom);
        bottomMenu.setItemIconTintList(null);
        frmbook = view.findViewById(R.id.frmbook);
        getFragmentManager().beginTransaction().replace(R.id.frmbook,new TheLoaiFragment()).commit();
        theLoaiFragment= new TheLoaiFragment();
        sachFragment = new SachFragment();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottommenu= view.findViewById(R.id.bottomngv);
        bottommenu.setOnNavigationItemSelectedListener(navListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectFragment=null;
            switch (menuItem.getItemId()){
                case R.id.itemloai:
                    selectFragment= new TheLoaiFragment();
                    break;
                case R.id.itemsach:
                   selectFragment= new SachFragment();
                    break;
            }

            getFragmentManager().beginTransaction().replace(R.id.frmbook,selectFragment).commit();
            return true;
        }
    };


}
