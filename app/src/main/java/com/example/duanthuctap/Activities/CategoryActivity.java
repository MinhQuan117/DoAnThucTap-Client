package com.example.duanthuctap.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duanthuctap.Adapter.ProductAdapter;
import com.example.duanthuctap.Api.ApiService;
import com.example.duanthuctap.Models.Category;
import com.example.duanthuctap.Models.Product;
import com.example.duanthuctap.R;
import com.example.duanthuctap.Tools.LIST;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private Category category;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        settingsToolbar();
        showListProduct();
    }

    private void settingsToolbar() {
        category = (Category) getIntent().getSerializableExtra("category");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(category.getName());
    }

    private void showListProduct(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_product);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CategoryActivity.this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        productAdapter = new ProductAdapter(CategoryActivity.this);
        recyclerView.setAdapter(productAdapter);
        getListProductByCategory(category.getName());
    }

    private void getListProductByCategory(String category) {
        if(LIST.listProductByCategory.isEmpty()) {
            ApiService.apiService.getListProductByCategory(category).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                    if (response.isSuccessful()&&response.body()!=null) {
                        response.body().sort((o1, o2) -> o1.getStatus().compareToIgnoreCase(o2.getStatus()));
                        LIST.listProductByCategory = response.body();
                        productAdapter.setData(LIST.listProductByCategory);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                    Toast.makeText(CategoryActivity.this, "Lỗi server!", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            productAdapter.setData(LIST.listProductByCategory);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
         searchView = (SearchView) menu.findItem(R.id.toolbar_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
           onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.prev_enter, R.anim.prev_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LIST.listProductByCategory.clear();
    }

}