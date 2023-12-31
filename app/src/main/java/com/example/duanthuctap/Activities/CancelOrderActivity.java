package com.example.duanthuctap.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.duanthuctap.Api.ApiService;
import com.example.duanthuctap.R;
import com.example.duanthuctap.Tools.ACCOUNT;
import com.example.duanthuctap.Tools.TOOLS;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edt_cob;
    private LinearLayout ln_mess_cob;
    private CheckBox cbox_cob;
    private Button btn_cob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancle_oder);
        mapping();
        setToolbar();
        cancelOderBill();
    }

    private void cancelOderBill() {
        btn_cob.setOnClickListener(v -> {
            if (edt_cob.getText().toString().isEmpty()) {
                ln_mess_cob.setVisibility(View.VISIBLE);
                return;
            }
            ln_mess_cob.setVisibility(View.GONE);
            if (!cbox_cob.isChecked()) {
                Toast.makeText(CancelOrderActivity.this, "Vui lòng xác nhận hủy đơn", Toast.LENGTH_SHORT).show();
                return;
            }
            String id_bill = getIntent().getStringExtra("id_bill");
            if (id_bill != null) {
                Dialog dialog = TOOLS.createDialog(CancelOrderActivity.this);
                dialog.show();
                ApiService.apiService.cancelBill(id_bill, ACCOUNT.user.getTokenNotify()).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null) {
                            int value;
                            if (response.body() == 1) {
                                value = 4;
                            } else {
                                value = 5;
                            }
                            CreateBillActivity.goResult(CancelOrderActivity.this, value);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                        Toast.makeText(CancelOrderActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_cob);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mapping() {
        btn_cob = findViewById(R.id.btn_cob);
        cbox_cob = findViewById(R.id.cbox_cob);
        ln_mess_cob = findViewById(R.id.ln_mess_cob);
        edt_cob = findViewById(R.id.edt_cob);
        toolbar = findViewById(R.id.toolbar_cob);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.prev_enter, R.anim.prev_exit);
    }

}