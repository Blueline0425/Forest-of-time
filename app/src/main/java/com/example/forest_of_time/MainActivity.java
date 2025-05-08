package com.example.forest_of_time;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nicknameEditText;
    private Button enterButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nicknameEditText = findViewById(R.id.nickname);
        enterButton = findViewById(R.id.enterButton);

        // 닉네임 저장소: 'UserPrefs' 라는 SharedPreferences 사용
        prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = nicknameEditText.getText().toString().trim();

                if (nickname.isEmpty()) {
                    Toast.makeText(MainActivity.this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (prefs.contains(nickname)) {
                    // 기존 사용자 → ScheduleActivity로 이동
                    Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                    intent.putExtra("nickname", nickname);
                    startActivity(intent);
                } else {
                    // 새로운 사용자 → 닉네임 저장하고 NewScheduleActivity로 이동
                    prefs.edit().putString(nickname, "created").apply();

                    Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                    intent.putExtra("nickname", nickname);
                    startActivity(intent);
                }
            }
        });
    }
}
