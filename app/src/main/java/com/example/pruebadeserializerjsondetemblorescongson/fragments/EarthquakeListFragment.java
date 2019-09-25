package com.example.pruebadeserializerjsondetemblorescongson.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pruebadeserializerjsondetemblorescongson.API.API;
import com.example.pruebadeserializerjsondetemblorescongson.API.APIServices.EarthquakeService;
import com.example.pruebadeserializerjsondetemblorescongson.R;
import com.example.pruebadeserializerjsondetemblorescongson.adapters.EarthquakeAdapter;
import com.example.pruebadeserializerjsondetemblorescongson.models.Earthquake;
import com.example.pruebadeserializerjsondetemblorescongson.models.EarthquakeReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getMaxResFromSharedPrefs;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getMinMagFromSharedPrefs;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getSortByFromSharedPrefs;


public class EarthquakeListFragment extends Fragment implements EarthquakeAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private SharedPreferences sharedPreferences;
    private RecyclerView.Adapter adapter;
    private List<Earthquake> earthquakeList;
    private SwipeRefreshLayout swipeRefreshLayout;


    public EarthquakeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_earthquake_list, container, false);
        referenceObjects();
        earthquakesReportRequest();
        return view;
    }

    private void referenceObjects() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        earthquakeList = new ArrayList<>();
        adapter = new EarthquakeAdapter(getContext(), R.layout.earthquake_item, earthquakeList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    private void earthquakesReportRequest() {
        swipeRefreshLayout.setRefreshing(true);
        //Crea una clase que implementa la interface que tenga el metodo getCity
        EarthquakeService earthquakeService = API.getAPI().create(EarthquakeService.class);
        Call<EarthquakeReport> earthquakeReportCall;

        earthquakeReportCall =
                earthquakeService.getReport("geojson", "NOW-1day",
                        "",
                        getMaxResFromSharedPrefs(Objects.requireNonNull(getContext()), sharedPreferences),
                        getMinMagFromSharedPrefs(getContext(), sharedPreferences),
                        getSortByFromSharedPrefs(getContext(), sharedPreferences));

        earthquakeReportCall.enqueue(new Callback<EarthquakeReport>() {
            @Override
            public void onResponse(Call<EarthquakeReport> call, Response<EarthquakeReport> response) {
                if (response.body().getEarthquakeList().size() == 0) {
                    Toast.makeText(getContext(), R.string.toast_no_earthquakes_found, Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
                earthquakeList.clear();
                earthquakeList.addAll(response.body().getEarthquakeList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<EarthquakeReport> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                if (!isWiFiEnabled())
                    Toast.makeText(getContext(), R.string.toast_no_earthquakes_found_due_to_internet, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), R.string.toast_no_data_found, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        earthquakesReportRequest();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        swipeRefreshLayout.setOnRefreshListener(null);
    }

    public void listaDeMayorAMenor() {
        Collections.sort(earthquakeList, new Comparator<Earthquake>() {
            @Override
            public int compare(Earthquake o1, Earthquake o2) {
                return o2.getComparator() - o1.getComparator();
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void listaDeMenorAMayor() {
        Collections.sort(earthquakeList, new Comparator<Earthquake>() {
            @Override
            public int compare(Earthquake o1, Earthquake o2) {
                return o1.getComparator() - o2.getComparator();
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void listaMasRecientesPrimero() {
        Collections.sort(earthquakeList, new Comparator<Earthquake>() {
            @Override
            public int compare(Earthquake o1, Earthquake o2) {
                return o2.getHourForComparator() - o1.getHourForComparator();
            }
        });
        adapter.notifyDataSetChanged();
    }

    private boolean isWiFiEnabled() {
        //Preguntar por el Wifi
        try {
            return (Settings.Global.getInt(getActivity().getContentResolver(), Settings.Global.WIFI_ON)) != 0;
        } catch (Settings.SettingNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onClick(Earthquake earthquake, int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(earthquakeList.get(position).getUrl())));
    }
}