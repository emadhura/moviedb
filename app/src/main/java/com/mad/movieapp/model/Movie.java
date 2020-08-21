package com.mad.movieapp.model;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    public String getPosterPath() {
        return posterPath;
    }

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("id")
    private String id;

    @SerializedName("release_date")
    private String releaseDate;

    public Movie(String title, String backdropPath, String id, String releaseDate) {
        this.title = title;
        this.backdropPath = backdropPath;
        this.id = id;
        this.releaseDate = releaseDate;
    }


    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
