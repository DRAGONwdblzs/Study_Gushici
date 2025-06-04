package com.example.study_gushici;


import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
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

        // 从XML资源文件加载诗词数据
        List<Poetry> poetryList = loadPoemsFromResources();

        adapter = new PoetryAdapter(poetryList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Poetry> loadPoemsFromResources() {
        List<Poetry> poems = new ArrayList<>();
        Resources res = getResources();
        XmlPullParser parser = res.getXml(R.xml.poems_data);

        try {
            int eventType = parser.getEventType();
            String title = null, author = null, dynasty = null, content = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("poem".equals(tagName)) {
                            title = author = dynasty = content = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        String text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (tagName) {
                            case "title":
                                title = parser.nextText();
                                break;
                            case "author":
                                author = parser.nextText();
                                break;
                            case "dynasty":
                                dynasty = parser.nextText();
                                break;
                            case "content":
                                content = parser.nextText();
                                break;
                            case "poem":
                                if (title != null && author != null && content != null) {
                                    poems.add(new Poetry(title, author, content, dynasty));
                                }
                                break;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return poems;
    }
}