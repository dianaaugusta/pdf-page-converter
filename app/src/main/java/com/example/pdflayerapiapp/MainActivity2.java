package com.example.pdflayerapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "Activity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toast.makeText(this, "onCreate Activity2", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate Activity2");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart Activity2", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart Activity2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart Activity2", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRestart Activity2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume Activity2", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onResume Activity2");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause Activity2", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause Activity2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop Activity2", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop Activity2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy Activity2", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy Activity2");
    }
}