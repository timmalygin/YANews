package yanews.malygin.tim.yanews.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static yanews.malygin.tim.yanews.util.IntentUtil.showActivity;

public class SplashActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showActivity(this, LoginActivity.class);
    }
}
