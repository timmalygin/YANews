package yanews.malygin.tim.yanews.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseUser;

import yanews.malygin.tim.yanews.R;
import yanews.malygin.tim.yanews.api.Api;
import yanews.malygin.tim.yanews.api.ApiKeys;
import yanews.malygin.tim.yanews.api.methods.LoginMethod;
import yanews.malygin.tim.yanews.util.Constant;
import yanews.malygin.tim.yanews.util.ViewUtils;

import static yanews.malygin.tim.yanews.util.IntentUtil.showActivity;
import static yanews.malygin.tim.yanews.util.ViewUtils.findById;
import static yanews.malygin.tim.yanews.util.ViewUtils.setClickListener;

/**
 *
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private View loadingView, buttonLogin;
    private EditText loginView, passwordView;
    private ProgressBar progress;

    @Nullable
    private LoginMethod apiMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingView = findById(this, R.id.loading);
        loginView = findById(this, R.id.login_text);
        passwordView = findById(this, R.id.password_text);
        buttonLogin = findById(this, R.id.login);
        progress = findById(this, R.id.view_progressbBar);
        setSupportActionBar(ViewUtils.<Toolbar>findById(this, R.id.toolbar));

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);

        setClickListener(this, R.id.login, this);
        setClickListener(this, R.id.demo, this);
        setClickListener(this, R.id.registration, this);

        ViewCompat.setTransitionName(loginView, Constant.LOGIN_TRANSITION_NAME);
        ViewCompat.setTransitionName(passwordView, Constant.PASSWORD_TRANSITION_NAME);

        updateButtonEnabled();
        apiMethod = Api.createMethod(ApiKeys.LOGIN);
    }

    @Override
    public void onStart() {
        super.onStart();
        loginView.addTextChangedListener(this);
        passwordView.addTextChangedListener(this);
        apiMethod.setCallback(new LoginMethod.LoginResult() {
            @Override
            public void onSuccess(@NonNull FirebaseUser user) {
                loadingView.setVisibility(View.GONE);
                showActivity(LoginActivity.this, MainActivity.class);
                finish();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        apiMethod.cancel();
        loginView.removeTextChangedListener(this);
        passwordView.removeTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                loadingView.setVisibility(View.VISIBLE);
                progress.requestFocus();
                final String login = loginView.getText().toString();
                final String password = passwordView.getText().toString();
                apiMethod.setLoginInformation(login, password)
                        .send();
                break;
            case R.id.demo:
                apiMethod.send();
                break;
            case R.id.registration:
                showActivity(this,
                        RegistrationActivity.class,
                        new Pair<View, String>(loginView, Constant.LOGIN_TRANSITION_NAME),
                        new Pair<View, String>(passwordView, Constant.PASSWORD_TRANSITION_NAME));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        updateButtonEnabled();
    }

    private void updateButtonEnabled() {
        buttonLogin.setEnabled(loginView.length() >= 8 && passwordView.length() >= 8);
    }
}
