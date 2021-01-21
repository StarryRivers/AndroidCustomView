package com.custom.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.custom.view.databinding.ActivityMainBinding;
import com.custom.view.upload.SelectPictureActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initEvent();
    }

    /**
     * Initial Event
     */
    private void initEvent() {
        binding.btnPicture.setOnClickListener(v -> {
            Intent selectPicture = new Intent(this, SelectPictureActivity.class);
            startActivity(selectPicture);
        });
    }
}
