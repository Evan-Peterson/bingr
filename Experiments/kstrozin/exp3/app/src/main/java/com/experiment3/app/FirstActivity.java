package com.experiment3.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

    private Button BtnMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        BtnMove = (Button) findViewById(R.id.Button1);

        BtnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToActivityTwo();
            }
        });

    }

    private void moveToActivityTwo() {
        Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
        startActivity((Intent) intent);
    }
}