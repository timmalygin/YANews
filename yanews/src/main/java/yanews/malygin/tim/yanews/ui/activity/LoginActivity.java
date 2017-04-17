package yanews.malygin.tim.yanews.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import yanews.malygin.tim.yanews.R;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;
import yanews.malygin.tim.yanews.util.Constant;
import yanews.malygin.tim.yanews.util.ViewUtils;

import static yanews.malygin.tim.yanews.util.IntentUtil.showActivity;
import static yanews.malygin.tim.yanews.util.ViewUtils.findById;
import static yanews.malygin.tim.yanews.util.ViewUtils.setClickListener;

/**
 *
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, FirebaseAuth.AuthStateListener, TextWatcher {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private View loadingView, buttonLogin;
    private EditText loginView, passwordView;

    @Nullable
    private SimpleIdlingResource idleLogin;

    @VisibleForTesting
    public void forTestInit(@NonNull SimpleIdlingResource idlingResource){
        idleLogin = idlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingView = findById(this, R.id.loading);
        loginView = findById(this, R.id.login_text);
        passwordView = findById(this, R.id.password_text);
        buttonLogin = findById(this, R.id.login);
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
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(this);
        loginView.addTextChangedListener(this);
        passwordView.addTextChangedListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        auth.removeAuthStateListener(this);
        loginView.removeTextChangedListener(this);
        passwordView.removeTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (idleLogin != null) {
                    idleLogin.waiting();
                }
                loadingView.setVisibility(View.VISIBLE);
                final String login = loginView.getText().toString();
                final String password = passwordView.getText().toString();
                auth.signInWithEmailAndPassword(login, password);
                break;
            case R.id.demo:
                auth.signInAnonymously();
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
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() != null) {
            if (idleLogin != null) {
                idleLogin.release();
            }
            loadingView.setVisibility(View.GONE);
            showActivity(this, MainActivity.class);
            finish();
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
