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
    private OnPoetryClickListener listener;

    public interface OnPoetryClickListener {
        void onPoetryClick(Poetry poetry);
    }

    public void setOnPoetryClickListener(OnPoetryClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvAuthor, tvPreview;
        private OnPoetryClickListener clickListener;
        private List<Poetry> poetryList;

        public ViewHolder(View itemView, OnPoetryClickListener clickListener, List<Poetry> poetryList) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvPreview = itemView.findViewById(R.id.tv_preview);
            this.clickListener = clickListener;
            this.poetryList = poetryList;

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clickListener != null) {
                    clickListener.onPoetryClick(poetryList.get(position));
                }
            });
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
        return new ViewHolder(view, listener, poetryList);
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