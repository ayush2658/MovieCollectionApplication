package com.brillicaservices.gurjas.firebasemoviessample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brillicaservices.gurjas.firebasemoviessample.database.DatabaseHelper;
import com.brillicaservices.gurjas.firebasemoviessample.movies.MovieListAdapter;
import com.brillicaservices.gurjas.firebasemoviessample.movies.MoviesModelView;
import com.brillicaservices.gurjas.firebasemoviessample.series.SeriesListAdapter;
import com.brillicaservices.gurjas.firebasemoviessample.series.SeriesModelView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AddNewItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Integer imagesArray[] = new Integer[]{R.drawable.avengers_infinity_war,
            R.drawable.avengers_infinity_war_imax_poster,
            R.drawable.dark_knight,
            R.drawable.deadpool,
            R.drawable.fast_furious_7,
            R.drawable.fight_club,
            R.drawable.godfather,
            R.drawable.hancock,
            R.drawable.harry_potter,
            R.drawable.inception,
            R.drawable.into_the_wild,
            R.drawable.iron_man_3,
            R.drawable.pursuit_of_happiness,
            R.drawable.john_wick,
            R.drawable.lion_king,
            R.drawable.rocky,
            R.drawable.shawshank_redemption,
            R.drawable.wanted};
    String cate="";
    String select_category[] = {"Select Genre","Movies", "Series"};
    private static final String TAG = MainActivity.class.getName();

    Spinner category, movieThumbnail;
    DatabaseHelper databaseHelper;
    EditText title,ReleaseYear,description;
    Button save,cancel;
    RatingBar rb;
    int selectedImage = 0;
    DatabaseReference databaseReference;
    ArrayList<MoviesModelView> moviesModelViewArrayList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_item);

        movieThumbnail = findViewById(R.id.item_image);
        category = findViewById(R.id.item_category);
        category.setOnItemSelectedListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        title = (EditText) findViewById(R.id.item_title);
        description = findViewById(R.id.item_description);
        rb = findViewById(R.id.item_rating);
        ReleaseYear = findViewById(R.id.release_year);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, select_category);
        category.setAdapter(arrayAdapter1);
        category.setPrompt(select_category[0]);

        ArrayAdapter<Integer> movieThumbnailAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item, imagesArray);
        movieThumbnail.setAdapter(movieThumbnailAdapter);

        movieThumbnail.setPrompt("Select Image");


        movieThumbnail.setOnItemSelectedListener(this);

        save = findViewById(R.id.save_item);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (category.getSelectedItem().equals("Movies")) {
                    databaseHelper = new DatabaseHelper(getApplicationContext());
                    String title_name = title.getText().toString();
                    int releaseyear = Integer.parseInt(ReleaseYear.getText().toString().trim());
                    String description_name = description.getText().toString();
                    float ra = rb.getRating();
                    databaseHelper.addNewMovies(new MoviesModelView(title_name, description_name, releaseyear, ra, selectedImage));
                    Toast.makeText(getApplicationContext(), "Movies data saved successfully", Toast.LENGTH_LONG).show();
                } else {


                    String title_name = title.getText().toString().trim();
                    String description_name = description.getText().toString().trim();
                    int release_year = Integer.parseInt(ReleaseYear.getText().toString().trim());
                    float rating = (float) rb.getRating();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("title", title_name);
                    hashMap.put("description", description_name);
                    hashMap.put("releaseYear", release_year);
                    hashMap.put("rating", rating);
                    Toast.makeText(AddNewItem.this, "Stored", Toast.LENGTH_LONG);
                    databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddNewItem.this, "Saved", Toast.LENGTH_LONG);
                            } else {
                                Toast.makeText(AddNewItem.this, "Not Stored", Toast.LENGTH_LONG);
                            }
                        }
                    });
                }
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedImage = imagesArray[position];
        cate=select_category[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
