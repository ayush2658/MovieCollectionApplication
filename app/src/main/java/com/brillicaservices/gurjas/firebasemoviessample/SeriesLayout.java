package com.brillicaservices.gurjas.firebasemoviessample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.brillicaservices.gurjas.firebasemoviessample.series.SeriesListAdapter;
import com.brillicaservices.gurjas.firebasemoviessample.series.SeriesModelView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeriesLayout extends AppCompatActivity implements SeriesListAdapter.ListItemClickListener {

    private DatabaseReference databaseReference;
    RecyclerView recyclerView;
    SeriesListAdapter seriesListAdapter;
    private static final String TAG = MainActivity.class.getName();
    private ArrayList<SeriesModelView> seriesModelViewArrayList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.series_layout);
        recyclerView=findViewById(R.id.recycler_view_series);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        seriesListAdapter=new SeriesListAdapter(seriesModelViewArrayList,this);
        recyclerView.setAdapter(seriesListAdapter);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"Child count: "+dataSnapshot);
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    try {
                        String seriesTitle = String.valueOf(snapshot.child("title").getValue());
                        String des = String.valueOf(snapshot.child("description").getValue());
                        int ra = Integer.parseInt(String.valueOf(snapshot.child("rating").getValue()));
                        int year = Integer.parseInt(String.valueOf(snapshot.child("releaseyear").getValue()));
                        int image = Integer.parseInt(String.valueOf(snapshot.child("image").getValue()));
                        seriesModelViewArrayList.add(new SeriesModelView(seriesTitle, des, year, ra, image));
                    } catch (Exception e) {

                    }
                }
//                    SeriesModelView seriesModelView=snapshot.getValue(SeriesModelView.class);
//                    seriesModelViewArrayList.add(seriesModelView);
                    seriesListAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onListItemClickListener(int clickedItemIndex) {
        Toast.makeText(getApplicationContext(),seriesModelViewArrayList.get(clickedItemIndex).name,Toast.LENGTH_SHORT).show();
    }
}
