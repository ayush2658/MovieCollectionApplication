package com.brillicaservices.gurjas.firebasemoviessample.series;

import android.support.design.widget.FloatingActionButton;

public class SeriesModelView {
    public  String name;
    public int releaseYear;
    public String description;
    public int rating;
    public int image;

    public SeriesModelView(String name, String description, int releaseYear, int rating, int image) {
        this.name = name;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.image = image;
    }

    public SeriesModelView() {
    }

    public SeriesModelView(FloatingActionButton fab) {
    }

    public int getImage() {
        return image;
    }

    public String getName(){ return  name;}

    public int getRating() {
        return rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setName(String name)
    {
        this.name=name;
    }
}
