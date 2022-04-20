package com.project.myambulancedriver.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.myambulancedriver.databinding.ItemListHistoryBinding;
import com.project.myambulancedriver.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<History> list = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<History> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListHistoryBinding itemListHistoryBinding = ItemListHistoryBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemListHistoryBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History data = list.get(position);
        holder.itemListHistoryBinding.tvNama2.setText(data.getUsername());
        holder.itemListHistoryBinding.tvAlamat2.setText(data.getAlamat());
        holder.itemListHistoryBinding.tvPlat2.setText(data.getNo_plat());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemListHistoryBinding itemListHistoryBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemListHistoryBinding = ItemListHistoryBinding.bind(itemView.getRootView());
        }
    }
}
