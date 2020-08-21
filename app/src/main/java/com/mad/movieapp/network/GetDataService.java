package com.mad.movieapp.network;

import com.mad.movieapp.model.Movie;
import com.mad.movieapp.model.MovieList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/3/movie/popular?api_key=21cf9e58fa9fb18d1769658101c2fa34")
    Call<MovieList> getAllMovies(@Query("page") int page);
}
