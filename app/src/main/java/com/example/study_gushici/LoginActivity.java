package com.example.study_gushici;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        findViewById(R.id.btnLogin).setOnClickListener(v -> attemptLogin());
        findViewById(R.id.tvRegister).setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        // 验证本地存储的用户信息
        String savedPassword = sharedPreferences.getString(username, "");
        if (!TextUtils.isEmpty(savedPassword) && savedPassword.equals(password)) {
            // 保存当前登录用户
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("current_user", username);
            editor.apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}