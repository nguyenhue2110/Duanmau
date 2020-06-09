package huentps08729.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import huentps08729.com.myapplication.fragment.BillFragment;
import huentps08729.com.myapplication.fragment.GioiThieuFragment;
import io.paperdb.Paper;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView ngvtrangchu;
    TextView txtTenNhanVien;
    Toolbar toolbar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        Paper.init(this);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        drawerLayout= findViewById(R.id.drwlayout);
        ngvtrangchu=findViewById(R.id.ngvtrangchu);
        View view= ngvtrangchu.getHeaderView(0);

        txtTenNhanVien=(TextView)view.findViewById(R.id.txtQuanlyct);
        Intent intent= getIntent();
        String tendn=intent.getStringExtra("tendn");
        txtTenNhanVien.setText(tendn);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.mo, R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
        ngvtrangchu.setItemIconTintList(null);
        ngvtrangchu.setNavigationItemSelectedListener(this);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction gt= fragmentManager.beginTransaction();
        GioiThieuFragment sachFragment = new GioiThieuFragment();
        gt.replace(R.id.content,sachFragment);
        gt.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){

            case R.id.Home:
                FragmentTransaction hm= fragmentManager.beginTransaction();
                GioiThieuFragment gioiThieuFragment = new GioiThieuFragment();
                hm.replace(R.id.content,gioiThieuFragment);
                hm.commit();
                menuItem.setChecked(true);
                setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();
                break;

            case R.id.User:
                FragmentTransaction us= fragmentManager.beginTransaction();
                UserFragment userFragment = new UserFragment();
                us.replace(R.id.content,userFragment);
                us.commit();
                menuItem.setChecked(true);
                setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();
                break;

            case R.id.Book:
                FragmentTransaction bk= fragmentManager.beginTransaction();
                BookFragment bookFragment = new BookFragment();
                bk.replace(R.id.content,bookFragment);
                bk.commit();
                menuItem.setChecked(true);
                setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();

                break;

            case R.id.setting:
//                FragmentTransaction set= fragmentManager.beginTransaction();
//                ProfileFragment profileFragment1= new ProfileFragment();
//                set.replace(R.id.content,profileFragment1);
//                set.commit();
//                menuItem.setChecked(true);
//                setTitle(menuItem.getTitle());
//                drawerLayout.closeDrawers();
                Intent intent1= new Intent(TrangChuActivity.this,ProfileActivity.class);
                intent1.putExtra("tendn1",txtTenNhanVien.getText().toString());
                startActivity(intent1);
                break;

            case R.id.Bill:
                FragmentTransaction gt= fragmentManager.beginTransaction();
                HoaDonFragment hoaDonFragment= new HoaDonFragment();
                gt.replace(R.id.content,hoaDonFragment);
                gt.commit();
                menuItem.setChecked(true);
                setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();
                break;



            case R.id.sbc:
                FragmentTransaction sbc= fragmentManager.beginTransaction();
                SaleFragment saleFragment= new SaleFragment();
                sbc.replace(R.id.content,saleFragment);
                sbc.commit();
                menuItem.setChecked(true);
                setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();
                break;
            case R.id.tk:
                FragmentTransaction tk= fragmentManager.beginTransaction();
                ThongkeFragment thongkeFragment= new ThongkeFragment();
                tk.replace(R.id.content,thongkeFragment);
                tk.commit();
                menuItem.setChecked(true);
                setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();
                break;
            case R.id.thoat:

                Paper.book().destroy();
                    Intent intent= new Intent(TrangChuActivity.this,DangNhapActivity.class);
                    startActivity(intent);
                break;

        }
        return true;
    }
}
