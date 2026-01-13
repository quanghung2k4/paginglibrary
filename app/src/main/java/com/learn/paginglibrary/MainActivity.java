package com.learn.paginglibrary;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.RequestManager;
import com.learn.paginglibrary.adapter.MovieLoadStateAdapter;
import com.learn.paginglibrary.adapter.MoviesAdapter;
import com.learn.paginglibrary.databinding.ActivityMainBinding;
import com.learn.paginglibrary.utils.GirdSpace;
import com.learn.paginglibrary.utils.MovieComparator;
import com.learn.paginglibrary.utils.Utils;
import com.learn.paginglibrary.viewmodel.MovieViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    MovieViewModel mainActivityViewModel;
    ActivityMainBinding binding;
    MoviesAdapter moviesAdapter;

    @Inject
    RequestManager requestManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Utils.API_KEY == null || Utils.API_KEY.isEmpty()){
            Toast.makeText(this, "error in API Key", Toast.LENGTH_SHORT).show();
        }
        moviesAdapter=new MoviesAdapter(new MovieComparator(),requestManager);
        mainActivityViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        initRecycleViewAndAdapter();

        //subcribe to paging data
        mainActivityViewModel.flowable.subscribe(moviePagingData -> {
            moviesAdapter.submitData(getLifecycle(),moviePagingData);
        });
    }

    private void initRecycleViewAndAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        binding.recycleViewMovie.setLayoutManager(gridLayoutManager);
        binding.recycleViewMovie.addItemDecoration(new GirdSpace(2,5,true));
        binding.recycleViewMovie.setAdapter(moviesAdapter.withLoadStateFooter(
                new MovieLoadStateAdapter(v -> {
                    moviesAdapter.retry();
                })
        ));
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return moviesAdapter.getItemViewType(position)==MoviesAdapter.LOADING_ITEM ? 1:2;
            }
        });
    }
}