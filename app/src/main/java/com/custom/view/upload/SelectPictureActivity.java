package com.custom.view.upload;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.custom.view.R;
import com.custom.view.databinding.ActivitySelectPictureBinding;
import com.custom.view.databinding.DialogChoosePicTypeBinding;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 从相机、图库选择图片
 *
 * @author StarryRivers
 */
public class SelectPictureActivity extends AppCompatActivity {
    private static final String TAG = "SelectPictureActivity";
    private ActivitySelectPictureBinding selectPictureBinding;
    String imgPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        selectPictureBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_picture);
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        initEvent();
    }

    /**
     * Initial Event
     */
    private void initEvent() {
        selectPictureBinding.ivHelpImageFirst.setOnClickListener(v -> {
            choosePictureDialog(10);
        });
        selectPictureBinding.ivHelpImageFirstDelete.setOnClickListener(v -> {
            selectPictureBinding.ivHelpImageFirstDelete.setVisibility(View.GONE);
            selectPictureBinding.ivHelpImageFirst.setImageResource(R.mipmap.ic_upload_image);
        });
        selectPictureBinding.ivHelpImageSecond.setOnClickListener(v -> {
            choosePictureDialog(11);
        });
        selectPictureBinding.ivHelpImageSecondDelete.setOnClickListener(v -> {
            selectPictureBinding.ivHelpImageSecondDelete.setVisibility(View.GONE);
            selectPictureBinding.ivHelpImageSecond.setImageResource(R.mipmap.ic_upload_image);
        });
        selectPictureBinding.ivHelpImageThird.setOnClickListener(v -> {
            choosePictureDialog(12);
        });
        selectPictureBinding.ivHelpImageThirdDelete.setOnClickListener(v -> {
            selectPictureBinding.ivHelpImageThirdDelete.setVisibility(View.GONE);
            selectPictureBinding.ivHelpImageThird.setImageResource(R.mipmap.ic_upload_image);
        });
    }

    /**
     * 选择拍照/相册图片
     *
     * @param type 打开类型区分码
     */
    private void choosePictureDialog(int type) {
        View chooseTypeView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_pic_type, null);
        AlertDialog selectDialog = new AlertDialog.Builder(this).setView(chooseTypeView).setCancelable(false).create();
        selectDialog.show();
        Objects.requireNonNull(selectDialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
        DialogChoosePicTypeBinding binding = DataBindingUtil.bind(chooseTypeView);
        binding.tvChoosePicCamera.setOnClickListener(v -> {
            selectDialog.dismiss();
            // 拉起相机
            openCamera(type);
        });
        binding.tvChoosePicGallery.setOnClickListener(v -> {
            selectDialog.dismiss();
            // 打开相册
            openGallery(type);
        });
        binding.tvChoosePicCancel.setOnClickListener(v -> selectDialog.dismiss());
    }
    /**
     * 打开相机
     *
     * @param type 打开类型区分码
     */
    private void openCamera(int type) {
        // 创建照片存储目录
        File imgDir = new File(getFilePath(null));
        // 创建照片
        String photoName = System.currentTimeMillis() + ".png";
        File picture = new File(imgDir, photoName);
        if (!picture.exists()) {
            try {
                picture.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "choosePictureTypeDialog: 创建图片失败", e);
            }
        }
        imgPath = picture.getAbsolutePath();
        // 调用相机拍照
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, "com.custom.view.fileprovider", picture));
        startActivityForResult(camera, type);
    }

    /**
     * 打开相册
     *
     * @param type 打开类型区分码
     */
    private void openGallery(int type) {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(gallery, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拉起相机回调data为null，打开相册回调不为null
        switch (requestCode) {
            case 0:
                Toast.makeText(this, "权限获得", Toast.LENGTH_SHORT).show();
                break;
            case 10:
                if (data == null && !imgPath.isEmpty()) {
                    Glide.with(this).load(imgPath).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(selectPictureBinding.ivHelpImageFirst);
                    selectPictureBinding.ivHelpImageFirstDelete.setVisibility(View.VISIBLE);
                    selectPictureBinding.ivHelpImageSecond.setVisibility(View.VISIBLE);
                } else if (data != null) {
                    Glide.with(this).load(data.getData()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(selectPictureBinding.ivHelpImageFirst);
                    selectPictureBinding.ivHelpImageFirstDelete.setVisibility(View.VISIBLE);
                    selectPictureBinding.ivHelpImageSecond.setVisibility(View.VISIBLE);
                }
                break;
            case 11:
                if (data == null && !imgPath.isEmpty()) {
                    Glide.with(this).load(imgPath).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(selectPictureBinding.ivHelpImageSecond);
                    selectPictureBinding.ivHelpImageSecondDelete.setVisibility(View.VISIBLE);
                    selectPictureBinding.ivHelpImageThird.setVisibility(View.VISIBLE);
                } else if (data != null) {
                    Glide.with(this).load(data.getData()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(selectPictureBinding.ivHelpImageSecond);
                    selectPictureBinding.ivHelpImageSecondDelete.setVisibility(View.VISIBLE);
                    selectPictureBinding.ivHelpImageThird.setVisibility(View.VISIBLE);
                }
                break;
            case 12:
                if (data == null && !imgPath.isEmpty()) {
                    Glide.with(this).load(imgPath).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(selectPictureBinding.ivHelpImageThird);
                    selectPictureBinding.ivHelpImageThirdDelete.setVisibility(View.VISIBLE);
                } else if (data != null) {
                    Glide.with(this).load(data.getData()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(selectPictureBinding.ivHelpImageThird);
                    selectPictureBinding.ivHelpImageThirdDelete.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
        imgPath = "";
    }

    /**
     * 获取存储文件路径
     *
     * @param dir     选择目录
     * @return 路径
     */
    public String getFilePath(String dir) {
        String path;
        // 判断是否有外部存储，是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = getExternalFilesDir(dir).getAbsolutePath();
        } else {
            // 使用内部储存
            path = getFilesDir() + File.separator + dir;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }
}
