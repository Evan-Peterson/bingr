package com.experiment3.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    private Button BtnMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        BtnMove = (Button) findViewById(R.id.Button2);

        BtnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToActivityOne();
            }
        });

    }

    private void moveToActivityOne() {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity((Intent) intent);
    }
}