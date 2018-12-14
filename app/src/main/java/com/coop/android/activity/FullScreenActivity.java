package com.coop.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.coop.android.R;

public class FullScreenActivity extends AppCompatActivity {
    private Button qrcodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        qrcodeBtn = (Button) findViewById(R.id.qrcodeBtn);

    }
}
