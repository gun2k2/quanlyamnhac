package com.example.quanlyamnhac.casi;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.quanlyamnhac.MainActivity;
import com.example.quanlyamnhac.R;
import com.example.quanlyamnhac.baihat.BaiHatActivity;
import com.example.quanlyamnhac.bieudien.BieuDienActivity;
import com.google.android.material.navigation.NavigationView;

public class CaSiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    Button casi_back;
    Button casi_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_si);

        casi_back = findViewById(R.id.casi_back);
        casi_fab = findViewById(R.id.casi_fab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navmenu);
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        CaSiFragment main = new CaSiFragment();
        FragmentManager FragManager = getSupportFragmentManager();
        FragmentTransaction FragTrans = FragManager.beginTransaction();
        FragTrans.replace(R.id.casi_fragment, main);
        FragTrans.commit();

        setEvent();
    }

    private void setEvent() {
        casi_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCaSiFragment fragment = new AddCaSiFragment();
                FragmentManager FragManager = getSupportFragmentManager();
                FragmentTransaction FragTrans = FragManager.beginTransaction();
                FragTrans.replace(R.id.casi_fragment, fragment);
                FragTrans.commit();
                casi_fab.setEnabled(false);
                casi_back.setEnabled(true);
            }
        });

        casi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaSiFragment main = new CaSiFragment();
                FragmentManager FragManager = getSupportFragmentManager();
                FragmentTransaction FragTrans = FragManager.beginTransaction();
                FragTrans.replace(R.id.casi_fragment, main);
                FragTrans.commit();
                casi_back.setEnabled(false);
                casi_fab.setEnabled(true);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_baihat:
                Intent baihat = new Intent(CaSiActivity.this, BaiHatActivity.class);
                baihat.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(baihat);
                break;
            case R.id.nav_bieudien:
                Intent bieudien = new Intent(CaSiActivity.this, BieuDienActivity.class);
                bieudien.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(bieudien);
                break;
            case R.id.nav_nhacsi:
                Intent nhacsi = new Intent(CaSiActivity.this, MainActivity.class);
                nhacsi.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(nhacsi);
                break;
            case R.id.nav_casi:
                Intent casi = new Intent(CaSiActivity.this, CaSiActivity.class);
                casi.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(casi);
                break;
//            case R.id.nav_thongke:
//                Intent thongke = new Intent(BaiHatActivity.this, ThongKeActivity.class);
//                thongke.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(thongke);
//                break;
//            case R.id.nav_baocao:
//                Intent baocao = new Intent(BaiHatActivity.this, BaoCaoActivity.class);
//                baocao.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(baocao);
//                break;

        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}