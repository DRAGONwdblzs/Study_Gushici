package com.example.study_gushici;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PoetryDetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvAuthor, tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry_detail);

        tvTitle = findViewById(R.id.tv_detail_title);
        tvAuthor = findViewById(R.id.tv_detail_author);
        tvContent = findViewById(R.id.tv_detail_content);

        // 获取传递过来的诗词数据
        Poetry poetry = getIntent().getParcelableExtra("poetry");
        if (poetry != null) {
            tvTitle.setText(poetry.getTitle());
            tvAuthor.setText(poetry.getAuthor());
            tvContent.setText(poetry.getContent());
        } else {
            // 处理诗词对象为空的情况
            // 例如显示一个提示信息
        }
    }
}