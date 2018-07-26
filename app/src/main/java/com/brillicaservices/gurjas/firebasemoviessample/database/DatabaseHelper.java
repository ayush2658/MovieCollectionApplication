package com.brillicaservices.gurjas.firebasemoviessample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brillicaservices.gurjas.firebasemoviessample.movies.MoviesModelView;
import com.brillicaservices.gurjas.firebasemoviessample.series.SeriesModelView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies_db";
    private static final String TABLE_NAME = "movies_record";
    private static final String MOVIES_TITLE= "movies_title";
    private static final String MOVIES_RELEASE_YEAR="movies_release_year";
    private static final String MOVIES_DESCRIPTION="movies_description";
    private static final String MOVIES_RATING="movies_rating";
    private static final String MOVIES_IMAGE="movies_image";
    private static final String MOVIES_ID = "movies_id";


    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            MOVIES_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MOVIES_TITLE + " TEXT, " + MOVIES_RELEASE_YEAR + " INTEGER," +
            MOVIES_DESCRIPTION + " TEXT, " + MOVIES_RATING + " INTEGER, " + MOVIES_IMAGE + " INTEGER); ";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public long addNewMovies(MoviesModelView moviesModelView)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MOVIES_TITLE, moviesModelView.title);
        contentValues.put(MOVIES_RELEASE_YEAR, moviesModelView.releaseYear);
        contentValues.put(MOVIES_DESCRIPTION, moviesModelView.description);
        contentValues.put(MOVIES_RATING, moviesModelView.rating);
        contentValues.put(MOVIES_IMAGE, moviesModelView.image);

        long id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        sqLiteDatabase.close();

        return id;
    }

    public List<MoviesModelView> allMoviesDetails() {
        List<MoviesModelView> moviesList = new ArrayList<>();

        String selectQuery = " SELECT * FROM " + TABLE_NAME ;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                MoviesModelView moviesModelView = new MoviesModelView();
                moviesModelView.setTitle(cursor.getString(cursor.getColumnIndex(MOVIES_TITLE)));
                moviesModelView.setReleaseYear(cursor.getInt(cursor.getColumnIndex(MOVIES_RELEASE_YEAR)));
                moviesModelView.setDescription(cursor.getString(cursor.getColumnIndex(MOVIES_DESCRIPTION)));
                moviesModelView.setRating(cursor.getInt(cursor.getColumnIndex(MOVIES_RATING)));
                moviesModelView.setImage(cursor.getInt(cursor.getColumnIndex(MOVIES_IMAGE)));

                moviesList.add(moviesModelView);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return  moviesList;
    }

    public int getMoviesCount() {

        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        int totalMoviesCount = cursor.getCount();
        cursor.close();

        return totalMoviesCount;
    }

//    public int updateIndividualMoviesDetails(MoviesModelView moviesModelView) {
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(MOVIES_TITLE, moviesModelView.getTitle());
//        values.put(MOVIES_RELEASE_YEAR, moviesModelView.getReleaseYear());
//        values.put(MOVIES_DESCRIPTION, moviesModelView.getDescription());
//        values.put(MOVIES_RATING, moviesModelView.getRating());
//        values.put(MOVIES_IMAGE, moviesModelView.getImage());
//
//        // updating row
//        return sqLiteDatabase.update(TABLE_NAME, values, MOVIES_ID + " = ?",
//                new String[]{String.valueOf(moviesModelView.getId())});
//    }

}
