package com.example.study_gushici;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 检查是否是首次启动，如果是则初始化诗词数据
        boolean isFirstLaunch = getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getBoolean("is_first_launch", true);
        if (isFirstLaunch) {
            // 初始化诗词数据
            new Thread(() -> {
                PoetryDataInitializer.initializePoetryData(this);
                // 标记为非首次启动
                getSharedPreferences("app_prefs", MODE_PRIVATE).edit()
                        .putBoolean("is_first_launch", false).apply();
            }).start();
        }

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // 默认显示诗词页面
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new PoetryFragment())
                .commit();

        // 设置底部导航栏的“诗词”项为选中状态
        bottomNav.setSelectedItemId(R.id.nav_poetry);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.nav_user) {
                        selectedFragment = new UserFragment();
                    } else if (item.getItemId() == R.id.nav_poetry) {
                        selectedFragment = new PoetryFragment();
                    } else if (item.getItemId() == R.id.nav_search) {
                        selectedFragment = new SearchFragment();
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                        return true;
                    }
                    return false;
                }
            };
}