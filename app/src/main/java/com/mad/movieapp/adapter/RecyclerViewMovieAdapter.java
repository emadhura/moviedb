package com.mad.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mad.movieapp.R;
import com.mad.movieapp.model.Movie;
import com.mad.movieapp.network.RetrofitClientinstance;

import java.util.List;

public class RecyclerViewMovieAdapter  extends  RecyclerView.Adapter<RecyclerViewMovieAdapter.MovieViewHolder> {

    private ItemClickListener mItemClickListener;

    private Context mContext;

    private int mItemLayout;

    private List<Movie> dataList;

    private LayoutInflater mInflater;

    public RecyclerViewMovieAdapter(Context context, List<Movie> dataList, int itemLayout){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mItemLayout = itemLayout;
        this.dataList = dataList;
    }

    public void setDataList(List<Movie> datalist){
        this.dataList = datalist;
    }

    public void setOnClickListener(ItemClickListener listener){
        this.mItemClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mItemLayout,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        if(dataList.get(position).getPosterPath()!= null) {

            Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+dataList.get(position).getPosterPath())
                    .placeholder(R.drawable.image_placeholder_back).into(holder.coverImage);
        }
        holder.txtTitle.setText(dataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView coverImage;
        TextView txtTitle;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImage = itemView.findViewById(R.id.coverImage);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener!=null){
                mItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
