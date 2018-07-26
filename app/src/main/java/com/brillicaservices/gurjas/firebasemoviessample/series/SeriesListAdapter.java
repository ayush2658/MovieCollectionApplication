package com.brillicaservices.gurjas.firebasemoviessample.series;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brillicaservices.gurjas.firebasemoviessample.R;
import com.brillicaservices.gurjas.firebasemoviessample.movies.MovieListAdapter;
import com.brillicaservices.gurjas.firebasemoviessample.movies.MoviesModelView;

import java.util.ArrayList;

public class SeriesListAdapter extends RecyclerView.Adapter<SeriesListAdapter.SeriesViewHolder> {

    private ArrayList<SeriesModelView> seriesModelViewArrayList;
    private SeriesListAdapter.ListItemClickListener itemClickListener;


    public SeriesListAdapter(ArrayList<SeriesModelView> seriesModelViewArrayList){
        this.seriesModelViewArrayList = seriesModelViewArrayList;
    }

     public SeriesListAdapter(ArrayList<SeriesModelView> seriesModelViewArrayList, ListItemClickListener itemClickListener) {
          this.seriesModelViewArrayList = seriesModelViewArrayList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public SeriesListAdapter.SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View itemView = layoutInflater.inflate(R.layout.recycler_list_item, parent, false);

        SeriesListAdapter.SeriesViewHolder seriesViewHolder = new SeriesListAdapter.SeriesViewHolder(itemView);

        return seriesViewHolder;
    }

    @Override
    public void onBindViewHolder(final SeriesViewHolder holder, int position) {
        SeriesModelView seriesModelView = seriesModelViewArrayList.get(position);

        holder.seriesThumbnail.setImageResource(seriesModelView.image);
        holder.seriesName.setText(seriesModelView.name);
        holder.seriesDescription.setText(seriesModelView.description);
        holder.rating.setText(""+seriesModelView.rating+"/5");

    }

    @Override
    public int getItemCount() {
       return seriesModelViewArrayList.size();
    }

    public interface ListItemClickListener {
        void onListItemClickListener(int clickedItemIndex);
    }
    public class SeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView seriesThumbnail;
        TextView seriesName,seriesDescription,rating;
        public SeriesViewHolder(View itemView) {
            super(itemView);
            seriesThumbnail = itemView.findViewById(R.id.movie_thumbnail);
            seriesName = itemView.findViewById(R.id.movie_name_title);
            seriesDescription = itemView.findViewById(R.id.movie_description);
            rating = itemView.findViewById(R.id.movie_rating);
        }

        @Override
        public void onClick(View view) {
            int clickedposition = getAdapterPosition();
            itemClickListener.onListItemClickListener(clickedposition);
        }
    }

}
