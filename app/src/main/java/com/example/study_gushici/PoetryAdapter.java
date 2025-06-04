package com.example.study_gushici;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.ViewHolder> {
    private List<Poetry> poetryList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvAuthor, tvPreview;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvPreview = itemView.findViewById(R.id.tv_preview);
        }
    }

    public PoetryAdapter(List<Poetry> poetryList) {
        this.poetryList = poetryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poetry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Poetry poetry = poetryList.get(position);
        holder.tvTitle.setText(poetry.getTitle());
        holder.tvAuthor.setText(poetry.getAuthor());

        // 显示内容前20个字符作为预览
        String content = poetry.getContent();
        holder.tvPreview.setText(content.length() > 20 ?
                content.substring(0, 20) + "..." : content);
    }

    @Override
    public int getItemCount() {
        return poetryList.size();
    }
}