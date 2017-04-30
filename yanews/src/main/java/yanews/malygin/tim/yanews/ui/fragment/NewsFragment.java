package yanews.malygin.tim.yanews.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yanews.malygin.tim.yanews.R;
import yanews.malygin.tim.yanews.data.News;

import static yanews.malygin.tim.yanews.util.ViewUtils.findById;

/**
 * Created by tim on 30.04.17.
 */
public class NewsFragment extends Fragment {

    private TextView title;
    private TextView body;
    private News news;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = findById(view, R.id.title);
        body = findById(view, R.id.body);
        updateUi();
    }

    public void setNews(@NonNull News news){
        this.news = news;
        updateUi();
    }

    private void updateUi() {
        if (title == null || news == null) {
            return;
        }

        title.setText(news.title);
        body.setText(news.body);
    }
}
