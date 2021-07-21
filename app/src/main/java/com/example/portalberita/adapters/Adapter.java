package com.example.portalberita.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalberita.DetailsPage;
import com.example.portalberita.R;
import com.example.portalberita.models.NewsArticle;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.HolderData> {

    List<NewsArticle> articles;
    LayoutInflater inflater;

    public Adapter(Context context, List<NewsArticle> articles){
        this.articles = articles;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Adapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_news, parent, false);
        return new HolderData(view);
    }
    @Override
    public void onBindViewHolder(HolderData holder, int position){
        holder.textNews.setText(articles.get(position).getTitle());

        Picasso.get()
                //*ERROR* disini gara gara image
                .load(articles.get(position).getUrlToImage())
                .resize(200, 200).centerCrop()
                .into(holder.imageView);

        holder.publishDate.setText(articles.get(position).getPublishedAt());
        holder.publisher.setText(articles.get(position).getSource().getName());

    }

    @Override
    public int getItemCount(){
        return articles.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView textNews;
        ImageView imageView;
        TextView publishDate;
        TextView publisher;

        public HolderData(@NonNull View itemView){
            super(itemView);

            textNews = itemView.findViewById(R.id.cardTitle);
            imageView = itemView.findViewById(R.id.cardImage);
            publishDate = itemView.findViewById(R.id.publishTime);
            publisher = itemView.findViewById(R.id.publisher);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailPage = new Intent(view.getContext(), DetailsPage.class);

                    detailPage.putExtra("title", articles.get(getAdapterPosition()).getTitle());
                    detailPage.putExtra("publishedAt", articles.get(getAdapterPosition()).getPublishedAt());
                    detailPage.putExtra("publisher", articles.get(getAdapterPosition()).getSource().getName());
                    detailPage.putExtra("author", articles.get(getAdapterPosition()).getAuthor());
                    detailPage.putExtra("description", articles.get(getAdapterPosition()).getDescription());
                    detailPage.putExtra("url", articles.get(getAdapterPosition()).getUrl());
                    detailPage.putExtra("imageUrl", articles.get(getAdapterPosition()).getUrlToImage());

                    view.getContext().startActivities(new Intent[]{detailPage});
                }
            });
        }
    }
}
