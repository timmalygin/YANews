package yanews.malygin.tim.yanews.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import yanews.malygin.tim.yanews.R;
import yanews.malygin.tim.yanews.api.Api;
import yanews.malygin.tim.yanews.api.ApiKeys;
import yanews.malygin.tim.yanews.api.methods.ApiMethod;
import yanews.malygin.tim.yanews.api.methods.RegistrationMethod;
import yanews.malygin.tim.yanews.ui.activity.MainActivity;
import yanews.malygin.tim.yanews.util.Constant;
import yanews.malygin.tim.yanews.util.ViewUtils;

import static yanews.malygin.tim.yanews.util.IntentUtil.showActivity;
import static yanews.malygin.tim.yanews.util.ViewUtils.findById;

public class RegistrationFragment extends Fragment implements TextWatcher, CompoundButton.OnCheckedChangeListener, RegistrationMethod.RegistrationResult {

    private EditText loginView;
    private EditText passwordView;
    private MenuItem registrationMenu;
    private RegistrationMethod registrationMethod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        loginView = findById(view, R.id.login);
        passwordView = findById(view, R.id.password);
        ViewUtils.<CheckBox>findById(view, R.id.visible_pwd).setOnCheckedChangeListener(this);

        ViewCompat.setTransitionName(loginView, Constant.LOGIN_TRANSITION_NAME);
        ViewCompat.setTransitionName(passwordView, Constant.PASSWORD_TRANSITION_NAME);
        registrationMethod = Api.createMethod(ApiKeys.REGISTRATION);
    }

    @Override
    public void onStart() {
        super.onStart();
        registrationMethod.setCallback(this);
        loginView.addTextChangedListener(this);
        passwordView.addTextChangedListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_registration, menu);
        registrationMenu = menu.findItem(R.id.action_registration);
        super.onCreateOptionsMenu(menu, inflater);
        updateMenuVisible();
    }

    @Override
    public void onStop() {
        super.onStop();
        registrationMethod.cancel();
        loginView.removeTextChangedListener(this);
        passwordView.removeTextChangedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_registration) {
            final String login = loginView.getText().toString();
            final String password = passwordView.getText().toString();
            registrationMethod.setRegistrationData(login, password).send();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        updateMenuVisible();
    }

    void updateMenuVisible() {
        if (registrationMenu == null || loginView == null || passwordView == null) {
            return;
        }

        if (loginView.length() < 8 || passwordView.length() < 8 || !isValidEmail(loginView.getText())) {
            registrationMenu.setEnabled(false);
            return;
        }
        registrationMenu.setEnabled(true);
    }

    private boolean isValidEmail(@Nullable CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        passwordView.setInputType(InputType.TYPE_CLASS_TEXT | (isChecked ?
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : InputType.TYPE_TEXT_VARIATION_PASSWORD));
    }

    @Override
    public void succes() {
        showActivity(getActivity(), MainActivity.class);
    }
}
