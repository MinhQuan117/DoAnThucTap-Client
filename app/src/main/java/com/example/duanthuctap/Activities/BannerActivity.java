package com.example.duanthuctap.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duanthuctap.Adapter.ProductAdapter;
import com.example.duanthuctap.Api.ApiService;
import com.example.duanthuctap.Models.Banner;
import com.example.duanthuctap.Models.Product;
import com.example.duanthuctap.R;
import com.example.duanthuctap.Tools.LIST;
import com.example.duanthuctap.Tools.TOOLS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerActivity extends AppCompatActivity {

    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        back();
        Banner banner = (Banner) getIntent().getSerializableExtra("banner");
        ImageView imageView = findViewById(R.id.imv_banner_event);
        RecyclerView recyclerView = findViewById(R.id.rcv_product_event);
        Glide.with(this)
                .load(TOOLS.doMainDevice + banner.getImage())
                .into(imageView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);
        getListProductByBanner(banner.getEvent());
    }

    private void getListProductByBanner(String code) {
        if (LIST.listProductByBanner.isEmpty()) {
            ApiService.apiService.getListProductByBanner(code).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                    if (response.isSuccessful()&&response.body()!=null) {
                        response.body().sort((o1, o2) -> o1.getStatus().compareToIgnoreCase(o2.getStatus()));
                        LIST.listProductByBanner = response.body();
                        productAdapter.setData(LIST.listProductByBanner);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                    Toast.makeText(BannerActivity.this, "Lỗi server!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            productAdapter.setData(LIST.listProductByBanner);
        }
    }

    private void back() {
        ImageView imv_back = findViewById(R.id.imv_back);
        imv_back.setOnClickListener(v -> onBackPressed());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.prev_enter, R.anim.prev_exit);
    }

}