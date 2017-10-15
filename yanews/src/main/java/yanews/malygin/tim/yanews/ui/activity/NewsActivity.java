package yanews.malygin.tim.yanews.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import yanews.malygin.tim.yanews.R;
import yanews.malygin.tim.yanews.data.News;
import yanews.malygin.tim.yanews.ui.fragment.NewsFragment;

public class NewsActivity extends AppCompatActivity {

    public static final String ARG_NEWS = "News";
    private News news;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (savedInstanceState == null) { //  это первый запуск
            Bundle bundle = getIntent().getExtras();
            news = bundle.getParcelable(ARG_NEWS);
        }

        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.news);
        newsFragment.setNews(news);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
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
