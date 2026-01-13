package com.learn.paginglibrary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.paginglibrary.R;
import com.learn.paginglibrary.databinding.LoadStateItemBinding;

public class MovieLoadStateAdapter extends LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder> {


    private View.OnClickListener mRetryCallback;

    public MovieLoadStateAdapter(View.OnClickListener mRetryCallback) {
        this.mRetryCallback = mRetryCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(viewGroup,mRetryCallback);
    }

    public class LoadStateViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        private TextView textView;
        private Button button;

        public LoadStateViewHolder(@NonNull ViewGroup parent,@NonNull View.OnClickListener retryCallBack) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_state_item,parent,false));

            LoadStateItemBinding binding=LoadStateItemBinding.bind(itemView);
            progressBar=binding.progressBar;
            textView=binding.errorMsg;
            button=binding.retryButton;
            button.setOnClickListener(retryCallBack);
        }

        public void bind(LoadState loadState){
            if(loadState instanceof LoadState.Error){
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                textView.setText(loadStateError.getError().getLocalizedMessage());

            }
            progressBar.setVisibility(
                    loadState instanceof LoadState.Loading ? View.VISIBLE :View.GONE);
            button.setVisibility(
                    loadState instanceof LoadState.Error ? View.VISIBLE :View.GONE);
            textView.setVisibility(
                    loadState instanceof LoadState.Error ? View.VISIBLE :View.GONE);
        }
    }
}
