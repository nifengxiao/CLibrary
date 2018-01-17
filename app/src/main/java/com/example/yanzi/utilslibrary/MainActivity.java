package com.example.yanzi.utilslibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import me.iwf.photopicker.PhotoPicker;

public class MainActivity extends AppCompatActivity {

    private android.widget.Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}
