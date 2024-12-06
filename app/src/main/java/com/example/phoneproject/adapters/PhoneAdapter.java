package com.example.phoneproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {
    private List<String> phoneNumbers;
    private LayoutInflater inflater;

    // интерфейс для обработки кликов
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String phoneNumber);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PhoneAdapter(Context context, List<String> phoneNumbers) {
        this.inflater = LayoutInflater.from(context);
        this.phoneNumbers = phoneNumbers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String phoneNumber = phoneNumbers.get(position);
        holder.phoneTextView.setText(phoneNumber);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(phoneNumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneNumbers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneTextView;

        ViewHolder(View itemView) {
            super(itemView);
            phoneTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}