package com.example.pruebadeserializerjsondetemblorescongson.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pruebadeserializerjsondetemblorescongson.R;
import com.example.pruebadeserializerjsondetemblorescongson.models.Earthquake;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.DAY_MONTHNAME_YEAR;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.TWELVE_HOUR_CLOCK;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getDateFormated;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getMagnitudeColor;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<Earthquake> earthquakeList;
    private OnItemClickListener listener;

    public EarthquakeAdapter(Context context, int layout, List<Earthquake> earthquakeList, OnItemClickListener listener) {
        this.context = context;
        this.layout = layout;
        this.earthquakeList = earthquakeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        this.context = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(earthquakeList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return this.earthquakeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView placeNear;
        private TextView placeCountry;
        private TextView mag;
        private TextView date;
        private TextView hour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            placeNear = itemView.findViewById(R.id.text_view_place_near);
            placeCountry = itemView.findViewById(R.id.text_view_place_country);
            mag = itemView.findViewById(R.id.text_view_mag);
            date = itemView.findViewById(R.id.text_view_date);
            hour = itemView.findViewById(R.id.text_view_hour);
        }

        public void bind(final Earthquake earthquake, final OnItemClickListener listener) {

            if (earthquake.getPlace().contains("of")) {
                placeNear.setText(earthquake.getPlace().substring(0, earthquake.getPlace().indexOf("f") + 1));
                placeCountry.setText(earthquake.getPlace().substring(earthquake.getPlace().indexOf("f") + 2));
            } else {
                placeNear.setText("Near the");
                placeCountry.setText(earthquake.getPlace());
            }

            //Recupera la referencia del background del texview
            GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

            //Obtiene el color dependiendo de la magnitud
            int magnitudeColor = getMagnitudeColor(context, earthquake.getMag());

            //Añade el color al background
            magnitudeCircle.setColor(magnitudeColor);

            mag.setText(new DecimalFormat("0.0").format(earthquake.getMag()));
            date.setText(getDateFormated(earthquake.getDate(), DAY_MONTHNAME_YEAR));
            hour.setText(getDateFormated(earthquake.getDate(), TWELVE_HOUR_CLOCK));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(earthquake, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(Earthquake earthquake, int position);
    }
}
