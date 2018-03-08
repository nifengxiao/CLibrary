package com.example.yanzi.utilslibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mylibrary.utils.pictureUtils.ImageToLoadHelper;

import java.util.logging.LoggingMXBean;

import me.iwf.photopicker.PhotoPicker;

public class MainActivity extends AppCompatActivity {

    private android.widget.Button btn;
    private android.widget.ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.img = (ImageView) findViewById(R.id.img);
        this.btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(MainActivity.this, PhotoPicker.REQUEST_CODE);
            }
        });
//        ImageToLoadHelper.loadImage(this,"http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg",img);
    }
}
