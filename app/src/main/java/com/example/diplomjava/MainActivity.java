package com.example.diplomjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int f = 1;
//        switch (f) {
//            case 1 -> {
//            }
//            case 2 -> {
//            }
//            case 3 -> {
//            }
//        }
        if(f==1){
            f = 2;
        }else if(f==2) {
            f = 3;
        }else if(f==3){
            f = 4;
        }
    }
}