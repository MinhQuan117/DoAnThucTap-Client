package com.example.duanthuctap.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.duanthuctap.R;


import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private int index_watting = 0;
    private TextView tv_loading;
    private ImageView imv_splash;
    private boolean isLoadingData = true;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_splash);
        tv_loading = findViewById(R.id.tv_loading);
        imv_splash = findViewById(R.id.imv_splash);
        Glide.with(this).asGif().load(R.drawable.spin).into(imv_splash);
        Glide.with(this).asGif().load(R.drawable.woman_shopping_online).into((ImageView) findViewById(R.id.bg_splash));
        //load data
        getUser();
        getAllBanner();
        getAllCategory();
        getAllProduct();
        getFavourite();
        getAddress();
        getRecently();
    }


    private void getRecently() {
        if (ACCOUNT.user!=null) {
            ApiService.apiService.getRecently(ACCOUNT.user.get_id()).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                       Collections.reverse(response.body());
                        LIST.listRecently = response.body();
                    }
                    gotoMainActivity();
                }

                @Override
                public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                    if (!isLoadingData) {
                        return;
                    }
                    ErrorLoadingData();
                }
            });
        }
    }


    public void getAddress() {
        if (ACCOUNT.user!=null) {
            ApiService.apiService.getAddress(ACCOUNT.user.get_id()).enqueue(new Callback<List<Address>>() {
                @Override
                public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            LIST.listAddress = response.body();
                        }
                    }
                    gotoMainActivity();
                }

                @Override
                public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable t) {
                    if (!isLoadingData) {
                        return;
                    }
                    ErrorLoadingData();
                }
            });
        }
    }

    private void getFavourite() {

        if (ACCOUNT.user != null) {
            ApiService.apiService.getListProductByFavourite(ACCOUNT.user.get_id()).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        if(response.body()!=null){
                            LIST.getListProductByFavourite = response.body();
                        }
                    }
                    gotoMainActivity();
                }

                @Override
                public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                    if (!isLoadingData) {
                        return;
                    }

                    ErrorLoadingData();
                }
            });
        }
    }

    private void getUser() {
        ACCOUNT.user = TOOLS.getUser(SplashActivity.this);
    }

    private void getAllCategory() {
        ApiService.apiService.getAllCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LIST.listCategory = response.body();
                }
                gotoMainActivity();
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                if(!isLoadingData){
                    return;
                }
                ErrorLoadingData();
            }
        });
    }

    private void getAllProduct() {
        ApiService.apiService.getAllProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    response.body().sort((o1, o2) -> o1.getStatus().compareToIgnoreCase(o2.getStatus()));
                    LIST.listProduct = response.body();
                }
                gotoMainActivity();
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                if(!isLoadingData){
                    return;
                }
                ErrorLoadingData();
            }
        });
    }

    private void getAllBanner() {
        ApiService.apiService.getAllBanner().enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(@NonNull Call<List<Banner>> call, @NonNull Response<List<Banner>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LIST.listBanner = response.body();
                }
                gotoMainActivity();
            }

            @Override
            public void onFailure(@NonNull Call<List<Banner>> call, @NonNull Throwable t) {
                if(!isLoadingData){
                    return;
                }
                ErrorLoadingData();
            }
        });
    }

    private void gotoMainActivity() {
        index_watting++;
        int index_max;
        if (ACCOUNT.user != null) {
            index_max = 6;
        }else {
            index_max = 3;
        }
        if (index_watting == index_max) {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }, 2000);

        }
    }

    @SuppressLint("SetTextI18n")
    private void ErrorLoadingData() {
        isLoadingData = false;
        tv_loading.setText("Kiểm tra lại tín hiệu đường truyền mạng!");
        tv_loading.setTextColor(Color.RED);
        Glide.with(this).asGif().load(R.drawable.earth).into(imv_splash);
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        View view = getLayoutInflater().inflate(R.layout.layout_message_error, null);
        Button button = view.findViewById(R.id.btn_exit);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this,SplashActivity.class);
            startActivity(intent);
            finishAffinity();
        });
        builder.setView(view);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}