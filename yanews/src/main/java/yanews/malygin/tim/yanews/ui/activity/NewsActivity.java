package yanews.malygin.tim.yanews.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import yanews.malygin.tim.yanews.R;
import yanews.malygin.tim.yanews.data.News;
import yanews.malygin.tim.yanews.ui.fragment.NewsFragment;

/**
 * Created by tim on 30.04.17.
 */
public class NewsActivity extends AppCompatActivity {

    public static final String ARG_NEWS = "News";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Bundle bundle = getIntent().getExtras();
        News news = bundle.getParcelable(ARG_NEWS);
        NewsFragment newsFragment  = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.news);
        newsFragment.setNews(news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
