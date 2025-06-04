package com.example.study_gushici;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        TextView tvUsername = view.findViewById(R.id.tv_username);
        // 从SharedPreferences获取当前登录用户
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = prefs.getString("current_user", "未登录");
        tvUsername.setText(username);

        Button btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            // 清除登录状态
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("current_user");
            editor.apply();

            Toast.makeText(requireContext(), "已退出登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        });

        return view;
    }
}