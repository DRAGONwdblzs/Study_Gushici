package com.example.study_gushici;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
        // 获取 TextView 组件
        TextView tvRegister = findViewById(R.id.tvRegister);

        // 设置点击事件监听器
        tvRegister.setOnClickListener(v -> {
            // 创建跳转到注册页面的 Intent
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent); // 启动注册页面
        });

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        findViewById(R.id.btnLogin).setOnClickListener(v -> attemptLogin());
        findViewById(R.id.tvRegister).setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        String savedPassword = sharedPreferences.getString(username, "");
        if (!TextUtils.isEmpty(savedPassword) && savedPassword.equals(password)) {
            // 保存当前用户（原逻辑保留）
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("current_user", username);
            editor.apply();

            // 新增：登录成功后，先跳转到欢迎界面
            Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
            welcomeIntent.putExtra("username", username); // 传递用户名
            startActivity(welcomeIntent);
            finish(); // 关闭登录页
        } else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}