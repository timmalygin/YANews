package yanews.malygin.tim.yanews.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import yanews.malygin.tim.yanews.R;

import static yanews.malygin.tim.yanews.util.IntentUtil.showActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getTitle(FirebaseAuth.getInstance().getCurrentUser()));
    }

    private String getTitle(@NonNull FirebaseUser user) {
        if (user.isAnonymous()) {
            return getString(R.string.str_anonomous);
        }
        if (TextUtils.isEmpty(user.getDisplayName())) {
            return getString(R.string.str_empty_name);
        }
        return user.getDisplayName();
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
                FirebaseAuth.getInstance().signOut();
                showActivity(this, LoginActivity.class);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
