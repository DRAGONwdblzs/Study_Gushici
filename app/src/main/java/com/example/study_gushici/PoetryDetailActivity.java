package com.example.study_gushici;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PoetryDetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvAuthor, tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry_detail);

        // 设置工具栏和返回按钮
        setupToolbar();

        // 初始化视图组件
        initViews();

        // 显示诗词内容
        displayPoetry();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("诗词详情");
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tv_detail_title);
        tvAuthor = findViewById(R.id.tv_detail_author);
        tvContent = findViewById(R.id.tv_detail_content);
    }

    private void displayPoetry() {
        // 获取传递的诗词数据
        Poetry poetry = getIntent().getParcelableExtra("poetry");

        if (poetry != null) {
            // 设置标题和作者
            tvTitle.setText(poetry.getTitle());
            tvAuthor.setText(poetry.getAuthor());

            // 格式化诗词内容（添加换行）
            String formattedContent = formatPoetryContent(poetry.getContent());
            tvContent.setText(formattedContent);
        } else {
            // 处理诗词数据为空的情况
            tvTitle.setText("诗词内容加载失败");
            tvAuthor.setText("");
            tvContent.setText("抱歉，诗词数据不存在或已被删除。");
        }
    }

    private String formatPoetryContent(String content) {
        if (TextUtils.isEmpty(content)) return "";

        // 在逗号、句号、问号等符号后添加换行
        return content
                .replace("，", "，\n")
                .replace("。", "。\n")
                .replace("？", "？\n")
                .replace("！", "！\n")
                .replace("；", "；\n");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}