package com.example.study_gushici;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private PoetryAdapter adapter;
    private List<Poetry> allPoems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.et_search);
        btnSearch = view.findViewById(R.id.btn_search);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 预先加载所有诗词
        allPoems = loadAllPoems();

        btnSearch.setOnClickListener(v -> performSearch());

        return view;
    }

    private void performSearch() {
        String query = etSearch.getText().toString().trim();
        if (query.isEmpty()) {
            return;
        }

        List<Poetry> results = new ArrayList<>();
        for (Poetry poem : allPoems) {
            if (poem.getTitle().contains(query) ||
                    poem.getAuthor().contains(query) ||
                    poem.getContent().contains(query)) {
                results.add(poem);
            }
        }

        if (results.isEmpty()) {
            // 显示无结果提示
            // 这里可以添加一个空状态视图
        } else {
            adapter = new PoetryAdapter(results);
            recyclerView.setAdapter(adapter);
        }
    }

    private List<Poetry> loadAllPoems() {
        // 实际项目中应从资源或数据库加载
        List<Poetry> poems = new ArrayList<>();
        poems.add(new Poetry("静夜思", "李白", "床前明月光，疑是地上霜。举头望明月，低头思故乡。", "唐"));
        poems.add(new Poetry("春晓", "孟浩然", "春眠不觉晓，处处闻啼鸟。夜来风雨声，花落知多少。", "唐"));
        poems.add(new Poetry("登鹳雀楼", "王之涣", "白日依山尽，黄河入海流。欲穷千里目，更上一层楼。", "唐"));
        poems.add(new Poetry("江雪", "柳宗元", "千山鸟飞绝，万径人踪灭。孤舟蓑笠翁，独钓寒江雪。", "唐"));
        poems.add(new Poetry("赋得古原草送别", "白居易", "离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。远芳侵古道，晴翠接荒城。又送王孙去，萋萋满别情。", "唐"));
        return poems;
    }
}