package com.example.pruebadeserializerjsondetemblorescongson.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

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
        earthquakeListFragment = new EarthquakeListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, earthquakeListFragment).commit();
        Picasso.get().load(R.drawable.eq_bg).fit().into((ImageView) navigationView.getHeaderView(0).findViewById(R.id.image_view));
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
