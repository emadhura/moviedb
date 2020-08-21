package com.mad.movieapp.ui.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.movieapp.R;
import com.mad.movieapp.adapter.RecyclerViewMovieAdapter;
import com.mad.movieapp.model.Movie;
import com.mad.movieapp.model.MovieList;
import com.mad.movieapp.network.GetDataService;
import com.mad.movieapp.network.RetrofitClientinstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment {

    private Context mContext;

    private ProgressBar progressBar;

    private RecyclerView mRecyclerView;

    private RecyclerViewMovieAdapter mMovieAdapter;

    private GridLayoutManager gridLayoutManager;

    private int lastLoadedPage;

    private GetDataService mDataService;

    private boolean loading = true;

    private int pastVisibleItems, visibleItemCount, totalItemCount;

    private List<Movie> mDataList;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mContext = getContext();
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = root.findViewById(R.id.spin_kit_progress);
        progressBar.setVisibility(View.GONE);

        mDataList = new ArrayList<>(0);

        mDataService = RetrofitClientinstance.getRetrofitInstance().create(GetDataService.class);
        lastLoadedPage = 1;
        loadNextDataPage(lastLoadedPage);


        mRecyclerView = root.findViewById(R.id.movieRecyclerView);
        gridLayoutManager = new GridLayoutManager(mContext,2, GridLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieAdapter = new RecyclerViewMovieAdapter(mContext,mDataList,R.layout.item_movie);
        mMovieAdapter.setOnClickListener(new RecyclerViewMovieAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "You clicked: "+mDataList.get(position).getTitle()
                        , Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mMovieAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            loadNextDataPage(lastLoadedPage++);
                        }
                    }
                }
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadNextDataPage(int page){
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieList> call = mDataService.getAllMovies(page);
        call.enqueue(new Callback<MovieList>() {

            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                //progressBar.setVisibility(View.INVISIBLE);
                hideProgress();
                loading = true;
                if(response.body()!=null && response.body().getMovies()!=null){
                    mDataList.addAll(response.body().getMovies());
                    generateDataList(mDataList);
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                //progressBar.setVisibility(View.INVISIBLE);
                hideProgress();
                loading = true;
                t.printStackTrace();
                Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<Movie> dataList){
        mMovieAdapter.setDataList(dataList);
        mMovieAdapter.notifyDataSetChanged();
    }


    private synchronized void hideProgress(){
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                progressBar.setVisibility(View.GONE);
                super.onPostExecute(o);
            }
        }.execute();
    }
}