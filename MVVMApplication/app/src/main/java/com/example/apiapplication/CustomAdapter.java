package com.example.apiapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiapplication.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private final Context context;
    private final List<NewsHeadlines> headlines;
    private final SelectListener listener;

    public CustomAdapter(Context context, List<NewsHeadlines> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
        // конструктор адаптера
        // listener включает интерфейс SelectListener который уведомляет об активации элементов списка
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false));
        // ViewHolder - один элемент из списка и заполняет макет из headline_list_items
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());
        // ну, для макета ViewHolder берет все данные из БД Postman и заполняет ими

        if (headlines.get(position).getUrlToImage()!=null) {
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnNewsClicked(headlines.get(position));
            }
        });
        // ну при нажатии будет открывать другой экран с полным описанием новости
    }

    @Override
    public int getItemCount() {
        return headlines.size();
        // при выходе из экрана с подробной информацией возвращает size recyclerView
    }
}
