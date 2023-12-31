package com.example.duanthuctap.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duanthuctap.Adapter.FavouriteAdapter;
import com.example.duanthuctap.Api.ApiService;
import com.example.duanthuctap.Models.Product;
import com.example.duanthuctap.R;
import com.example.duanthuctap.Tools.ACCOUNT;
import com.example.duanthuctap.Tools.LIST;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FavouriteAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        mapping();
        setToolbar();
        showFavourite();
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolbar_favourite);
        recyclerView = findViewById(R.id.rcv_favourite);
    }

    private void showFavourite() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavouriteActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        adapter = new FavouriteAdapter(this,true);
        recyclerView.setAdapter(adapter);
        getListProduct();
    }

    private void getListProduct() {
        if (LIST.getListProductByFavourite.isEmpty()) {
            ApiService.apiService.getListProductByFavourite(ACCOUNT.user.get_id()).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                    if (response.isSuccessful()&& response.body()!=null) {
                        response.body().sort((o1, o2) -> o1.getStatus().compareToIgnoreCase(o2.getStatus()));
                        LIST.getListProductByFavourite = response.body();
                        adapter.setData(LIST.getListProductByFavourite);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {

                }
            });
        } else {
            adapter.setData(LIST.getListProductByFavourite);
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_favourite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.toolbar_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.prev_enter, R.anim.prev_exit);
    }
}