package com.example.portalberita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.portalberita.adapters.Adapter;
import com.example.portalberita.models.EverythingFilter;
import com.example.portalberita.models.NewsArticle;
import com.example.portalberita.models.TopHeadlinesFilter;
import com.example.portalberita.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FilterDialog.ExampleDialogListener{
    private List<NewsArticle> newsArticles;
    private Adapter newsAdapter;
    private RecyclerView recyclerView;
    private NewsViewModel newsViewModel;
    private TopHeadlinesFilter topHeadlinesFilter;
    private EverythingFilter everythingFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvListNews);
        topHeadlinesFilter = new TopHeadlinesFilter();
        everythingFilter = new EverythingFilter();
        newsArticles = new ArrayList<NewsArticle>();

        //Inisiasi view model
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        //Get data top-headline dari indonesia
        topHeadlinesFilter.setCountry("id");
        newsViewModel.init(topHeadlinesFilter);
        updateNews();

        //Inisiasi recycler view
        initializationRecyclerView();

        //Dialog
        Button filterButton = (Button) findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu , menu);

        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchKey) {
                topHeadlinesFilter.setSearchKey(searchKey);
                newsViewModel.getTopHeadline(topHeadlinesFilter);
                updateNews();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchKey) {
                if(searchKey.isEmpty()){
                    //Get data top-headline dari indonesia
                    topHeadlinesFilter.setCountry("id");
                    topHeadlinesFilter.setCategory("");
                    topHeadlinesFilter.setSearchKey("");
                    newsViewModel.getTopHeadline(topHeadlinesFilter);
                    updateNews();
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    public void updateNews(){
        newsViewModel.getNewsRepository().observe(this, newsResponse -> {
            newsArticles.clear();
            if(newsResponse.getTotalResults() == 0){
                Toast.makeText(this, "Berita yang dicari tidak ada", Toast.LENGTH_SHORT).show();
            }else{
                newsArticles.addAll(newsResponse.getArticles());
            }
            newsAdapter.notifyDataSetChanged();
        });
    }

    private void initializationRecyclerView() {
        if (newsAdapter == null) {
            newsAdapter = new Adapter(this, newsArticles);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(newsAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }
    }

    public void openDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getSupportFragmentManager(), "filter dialog");
    }

    @Override
    public void finishFilter(String country, String category) {
        topHeadlinesFilter.setCountry(country);
        topHeadlinesFilter.setCategory(category);
        newsViewModel.getTopHeadline(topHeadlinesFilter);
        updateNews();
    }
}