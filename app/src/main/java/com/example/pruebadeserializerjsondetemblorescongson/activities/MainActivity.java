package com.example.pruebadeserializerjsondetemblorescongson.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pruebadeserializerjsondetemblorescongson.R;
import com.example.pruebadeserializerjsondetemblorescongson.fragments.EarthquakeListFragment;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private EarthquakeListFragment earthquakeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        referenceObjects();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Añade el icono de menu lateral
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_lateral);
        //Muestra el icono
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Titulo del toolbar
        getSupportActionBar().setTitle(R.string.main_activity_title);

    }

    private void referenceObjects() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        final LinearLayout content = findViewById(R.id.content);
        earthquakeListFragment = new EarthquakeListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, earthquakeListFragment).commit();
        Picasso.get().load(R.drawable.eq_bg).fit().into((ImageView) navigationView.getHeaderView(0).findViewById(R.id.image_view));
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, 0, 0) {
            //private float scaleFactor = 6f;//Para escalar

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                content.setTranslationX(slideX);
                //content.setScaleX(1 - (slideOffset / scaleFactor));//Para escalar
                //content.setScaleY(1 - (slideOffset / scaleFactor));//Para escalar
            }
        };
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //drawerLayout.setDrawerElevation(0f);//Para escalar

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_major_minor:
                earthquakeListFragment.listaDeMayorAMenor();
                break;
            case R.id.menu_minor_major:
                earthquakeListFragment.listaDeMenorAMayor();
                break;
            case R.id.menu_by_hour:
                earthquakeListFragment.listaMasRecientesPrimero();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            default:
                return false;
        }
        return true;
    }

    //Aunque parezca una mamada, si se pone el else y no el else if, se cierra el menu lateral
    //y también la app
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            finish();
        }
    }
}
