package com.example.flowmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.forgerock.android.auth.FRListener;
import org.forgerock.android.auth.FRUser;

public class MainActivity extends AppCompatActivity implements FRListener<Void> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSuccess(Void result) {
        FRUser.getCurrentUser();
        Intent intent = new Intent(this, UserinfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onException(Exception e) {
        System.out.println("e = " + e.getMessage());
    }
}