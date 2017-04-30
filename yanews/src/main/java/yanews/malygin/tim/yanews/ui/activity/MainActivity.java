package yanews.malygin.tim.yanews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import yanews.malygin.tim.yanews.R;
import yanews.malygin.tim.yanews.api.Api;
import yanews.malygin.tim.yanews.api.ApiKeys;
import yanews.malygin.tim.yanews.api.methods.LogoutMethod;
import yanews.malygin.tim.yanews.data.News;
import yanews.malygin.tim.yanews.ui.adapter.NewsAdapter;
import yanews.malygin.tim.yanews.ui.fragment.NewsFragment;
import yanews.malygin.tim.yanews.ui.fragment.NewsTitleFragment;

import static yanews.malygin.tim.yanews.util.IntentUtil.showActivity;

public class MainActivity extends AppCompatActivity implements LogoutMethod.LogoutResult, NewsAdapter.OnNewsSelectedListener {

    private LogoutMethod apiMethod;
    private boolean isPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getTitle(FirebaseAuth.getInstance().getCurrentUser()));
        apiMethod = Api.createMethod(ApiKeys.LOGOUT);
        isPhone = getResources().getBoolean(R.bool.is_phone);
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiMethod.setCallback(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        apiMethod.cancel();
    }

    private String getTitle(@NonNull FirebaseUser user) {
        if (user.isAnonymous()) {
            return getString(R.string.str_anonomous);
        }
        if (TextUtils.isEmpty(user.getEmail())) {
            return getString(R.string.str_empty_name);
        }
        return user.getEmail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                apiMethod.send();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void succes() {
        showActivity(this, LoginActivity.class);
        finish();
    }

    @Override
    public void onNewsSelect(@NonNull News news) {
        if(isPhone){
            Intent intent = new Intent(this, NewsActivity.class);
            intent.putExtra(NewsActivity.ARG_NEWS, news);
            startActivity(intent);
        }else{
            NewsFragment newsFragment = (yanews.malygin.tim.yanews.ui.fragment.NewsFragment) getSupportFragmentManager().findFragmentById(R.id.news);
            newsFragment.setNews(news);
        }
    }


}
