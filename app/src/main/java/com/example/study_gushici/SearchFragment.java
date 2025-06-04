package com.example.study_gushici;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        allPoems = loadAllPoems(getContext());

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

    private List<Poetry> loadAllPoems(Context context) {
        List<Poetry> poems = new ArrayList<>();
        PoetryDatabaseHelper dbHelper = new PoetryDatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PoetryDatabaseHelper.COLUMN_TITLE,
                PoetryDatabaseHelper.COLUMN_AUTHOR,
                PoetryDatabaseHelper.COLUMN_DYNASTY,
                PoetryDatabaseHelper.COLUMN_CONTENT
        };

        Cursor cursor = db.query(
                PoetryDatabaseHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(PoetryDatabaseHelper.COLUMN_TITLE));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(PoetryDatabaseHelper.COLUMN_AUTHOR));
            String dynasty = cursor.getString(cursor.getColumnIndexOrThrow(PoetryDatabaseHelper.COLUMN_DYNASTY));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(PoetryDatabaseHelper.COLUMN_CONTENT));
            poems.add(new Poetry(title, author, content, dynasty));
        }

        cursor.close();
        db.close();
        return poems;
    }
}