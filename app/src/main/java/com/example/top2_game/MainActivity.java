package com.example.top2_game;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 2;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kích hoạt chế độ EdgeToEdge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Lấy ID của các thành phần trong layout
        clickButton();
        imageView = findViewById(R.id.imageIcon);
        Glide.with(this)
                .load(R.drawable.image_icon) // Your image resource
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);

        // Thêm sự kiện click để chọn ảnh đại diện từ thư viện
        imageView.setOnClickListener(v -> requestReadExternalStoragePermission());
    }

    private void requestReadExternalStoragePermission() {
        // Kiểm tra quyền truy cập bộ nhớ
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa có quyền, yêu cầu quyền từ người dùng
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            // Nếu đã có quyền, mở thư viện để chọn ảnh
            openImagePicker();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                Glide.with(this)
                        .load(selectedImageUri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageView);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Permission denied to read external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clickButton() {
        // Button để bắt đầu trò chơi
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        //Button để mở hướng dẫn
        Button btnInstruct = findViewById(R.id.btnInstruct);
        View alertInstructDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog_instruct, null);
        AlertDialog.Builder instructDialog = new AlertDialog.Builder(MainActivity.this);
        instructDialog.setView(alertInstructDialog);
        instructDialog.setCancelable(false);
       //Button để ở hôp thoại
        final AlertDialog dialogInstruct = instructDialog.create();
        ImageButton cancelButtonInstruct = alertInstructDialog.findViewById(R.id.cancelID);
        btnInstruct.setOnClickListener(v -> {
            Objects.requireNonNull(dialogInstruct.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogInstruct.show();
        });
        // Hủy hộp thoại khi nhấn nút hủy
        cancelButtonInstruct.setOnClickListener(v -> dialogInstruct.cancel());

        // Button để mở cài đặt
        Button btnSetting = findViewById(R.id.btnSetting);
        View alertCustomDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog_setting, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(alertCustomDialog);
        alertDialog.setCancelable(false);

        // Button để hủy hộp thoại
        ImageButton cancelButton = alertCustomDialog.findViewById(R.id.cancelID);
        final AlertDialog dialog = alertDialog.create();

        btnSetting.setOnClickListener(v -> {
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        // Hủy hộp thoại khi nhấn nút hủy
        cancelButton.setOnClickListener(v -> dialog.cancel());

        // Button để thoát ứng dụng
        Button btnEnd = findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(v -> finish());
    }
}
