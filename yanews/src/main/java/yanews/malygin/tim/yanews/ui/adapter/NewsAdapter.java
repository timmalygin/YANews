package yanews.malygin.tim.yanews.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yanews.malygin.tim.yanews.R;
import yanews.malygin.tim.yanews.data.News;

import static yanews.malygin.tim.yanews.util.ViewUtils.findById;


/**
 * Created by tim on 30.04.17.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private final List<News> newsList = new ArrayList<>();
    private final OnNewsSelectedListener newsListener;

    public NewsAdapter(OnNewsSelectedListener newsListener) {
        this.newsListener = newsListener;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(parent, newsListener);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.bind(this.newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setNews(@Nullable List<News> news){
        this.newsList.clear();
        if(news!=null && news.size()>0) {
            this.newsList.addAll(news);
        }
        notifyDataSetChanged();
    }

    static class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView titleView, bodyView;
        final OnNewsSelectedListener listener;

        NewsHolder(@NonNull ViewGroup parent, OnNewsSelectedListener listener) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_news_item, parent, false));
            this.listener = listener;
            titleView = findById(itemView, R.id.title);
            bodyView = findById(itemView, R.id.body);
            itemView.setOnClickListener(this);
        }

        void bind(@NonNull News news){
            titleView.setText(news.title);
            if(bodyView!=null) {
                bodyView.setText(news.body);
            }
            itemView.setTag(news);
        }

        @Override
        public void onClick(View v) {
            News news = (News) v.getTag();
            listener.onNewsSelect(news);
        }
    }

    public interface OnNewsSelectedListener{
        void onNewsSelect(@NonNull News news);
    }
}
