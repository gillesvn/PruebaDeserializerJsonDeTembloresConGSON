package com.example.pruebadeserializerjsondetemblorescongson.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pruebadeserializerjsondetemblorescongson.R;
import com.example.pruebadeserializerjsondetemblorescongson.models.Earthquake;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.DAY_MONTHNAME_YEAR;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.TWELVE_HOUR_CLOCK;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getDateFormated;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getMagnitudeColor;

public class EarthquakeAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Earthquake> earthquakeList;

    public EarthquakeAdapter(Context context, int layout, List<Earthquake> earthquakeList) {
        this.context = context;
        this.layout = layout;
        this.earthquakeList = earthquakeList;
    }

    @Override
    public int getCount() {
        return earthquakeList.size();
    }

    @Override
    public Object getItem(int position) {
        return earthquakeList.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(this.layout, null);
            viewHolder = new ViewHolder();
            viewHolder.placeNear = view.findViewById(R.id.text_view_place_near);
            viewHolder.placeCountry = view.findViewById(R.id.text_view_place_country);
            viewHolder.mag = view.findViewById(R.id.text_view_mag);
            viewHolder.date = view.findViewById(R.id.text_view_date);
            viewHolder.hour = view.findViewById(R.id.text_view_hour);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (earthquakeList.get(position).getPlace().contains("of")) {
            viewHolder.placeNear.setText(earthquakeList.get(position).getPlace().substring(0, earthquakeList.get(position).getPlace().indexOf("f") + 1));
            viewHolder.placeCountry.setText(earthquakeList.get(position).getPlace().substring(earthquakeList.get(position).getPlace().indexOf("f") + 2));
        } else {
            viewHolder.placeNear.setText("Near the");
            viewHolder.placeCountry.setText(earthquakeList.get(position).getPlace());
        }

        //Recupera la referencia del background del texview
        GradientDrawable magnitudeCircle = (GradientDrawable) viewHolder.mag.getBackground();

        //Obtiene el color dependiendo de la magnitud
        int magnitudeColor = getMagnitudeColor(context, earthquakeList.get(position).getMag());

        //Añade el color al background
        magnitudeCircle.setColor(magnitudeColor);


        viewHolder.mag.setText(new DecimalFormat("0.0").format(earthquakeList.get(position).getMag()));
        viewHolder.date.setText(getDateFormated(earthquakeList.get(position).getDate(), DAY_MONTHNAME_YEAR));
        viewHolder.hour.setText(getDateFormated(earthquakeList.get(position).getDate(), TWELVE_HOUR_CLOCK));
        return view;
    }

    static class ViewHolder {
        private TextView placeNear;
        private TextView placeCountry;
        private TextView mag;
        private TextView date;
        private TextView hour;
    }
}
