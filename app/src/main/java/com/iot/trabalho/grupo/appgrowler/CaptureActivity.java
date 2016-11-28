package com.iot.trabalho.grupo.appgrowler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class CaptureActivity extends AppCompatActivity {
    static final String TAG="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d(TAG, "contents: " + contents);
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "RESULT_CANCELED");
            }
        }

    }
}
