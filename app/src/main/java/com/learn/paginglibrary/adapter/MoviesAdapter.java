package com.learn.paginglibrary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.learn.paginglibrary.databinding.SingleMovieItemBinding;
import com.learn.paginglibrary.model.Movie;

import kotlin.coroutines.CoroutineContext;

public class MoviesAdapter extends PagingDataAdapter<Movie,MoviesAdapter.MovieViewHolder> {
    public static final int LOADING_ITEM=0;
    public static final int MOVIE_ITEM=1;
    RequestManager glide;

    public MoviesAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback, RequestManager gidle) {
        super(diffCallback);
        this.glide = gidle;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(
                SingleMovieItemBinding
                        .inflate(LayoutInflater.from(parent.getContext()),
                        parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie current= getItem(position);
        if(current != null){
            glide.load("https://image.tmdb.org/t/p/w500"+current.getPosterPath())
                    .into(holder.binding.imageViewMovie);
            holder.binding.textViewRating.setText(String.valueOf(current.getVoteAverage()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() ?  MOVIE_ITEM:LOADING_ITEM;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        private SingleMovieItemBinding binding;

        public MovieViewHolder(@NonNull SingleMovieItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
