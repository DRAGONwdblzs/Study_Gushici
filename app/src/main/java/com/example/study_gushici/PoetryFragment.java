package com.example.study_gushici;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class PoetryFragment extends Fragment {
    private RecyclerView recyclerView;
    private PoetryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poetry, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 从数据库加载诗词数据
        List<Poetry> poetryList = loadPoemsFromDatabase(getContext());

        adapter = new PoetryAdapter(poetryList);
        recyclerView.setAdapter(adapter);

        // 设置诗词项的点击监听器
        adapter.setOnPoetryClickListener(poetry -> {
            Intent intent = new Intent(getActivity(), PoetryDetailActivity.class);
            intent.putExtra("poetry", poetry);
            startActivity(intent);
        });

        return view;
    }

    private List<Poetry> loadPoemsFromDatabase(Context context) {
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