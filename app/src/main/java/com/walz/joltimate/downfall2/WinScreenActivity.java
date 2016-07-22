package com.walz.joltimate.downfall2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

public class WinScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        AppCompatButton nextLevelButton = (AppCompatButton) findViewById(R.id.retry_button);
        nextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DownFallActivity.class);
                startActivity(intent);
            }
        });
        AppCompatButton backButton = (AppCompatButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                startActivity(intent);
            }
        });
        AppCompatTextView textView = (AppCompatTextView) findViewById(R.id.level_textview);
        textView.setText("Chapter: " + (Levels.currentSection + 1) + ", Level: " + (Levels.currentLevel));
    }
}
