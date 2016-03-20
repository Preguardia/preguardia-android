package com.preguardia.app.user.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.general.Constants;
import com.preguardia.app.main.MainActivity;

import net.grandcentrix.tray.TrayAppPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author amouly on 2/20/16.
 */
public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @Bind(R.id.user_register_toolbar)
    Toolbar toolbar;
    @Bind(R.id.user_register_patient)
    Button patientButton;
    @Bind(R.id.user_register_medic)
    Button medicButton;

    @Bind(R.id.user_register_name)
    TextInputLayout nameInputView;
    @Bind(R.id.user_register_email)
    TextInputLayout emailInputView;
    @Bind(R.id.user_register_password)
    TextInputLayout passwordInputView;
    @Bind(R.id.user_register_date)
    TextInputLayout dateInputView;
    @Bind(R.id.user_register_medical)
    TextInputLayout medicalInputView;
    @Bind(R.id.user_register_phone)
    TextInputLayout phoneInputView;
    @Bind(R.id.user_input_plate)
    TextInputLayout plateInputView;
    @Bind(R.id.user_register_plate_container)
    LinearLayout plateContainerView;
    @Bind(R.id.user_register_medical_container)
    LinearLayout medicalContainerView;

    private RegisterContract.UserActionsListener mActionListener;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Init the Presenter
        mActionListener = new RegisterPresenter(new Firebase(Constants.FIREBASE_URL), new TrayAppPreferences(this), this);

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(this)
                .title(R.string.user_register_title)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        patientButton.setPressed(true);
    }

    @SuppressWarnings("unused")
    @OnTouch(R.id.user_register_patient)
    public boolean onPatientClick() {
        medicalContainerView.setVisibility(View.VISIBLE);
        plateContainerView.setVisibility(View.GONE);

        patientButton.setPressed(true);
        medicButton.setPressed(false);

        return true;
    }

    @SuppressWarnings("unused")
    @OnTouch(R.id.user_register_medic)
    public boolean onMedicClick() {
        medicalContainerView.setVisibility(View.GONE);
        plateContainerView.setVisibility(View.VISIBLE);

        patientButton.setPressed(false);
        medicButton.setPressed(true);

        return true;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.register_medic_button)
    public void onRegisterClick() {
        String type = null;
        String name = nameInputView.getEditText().getText().toString();
        String email = emailInputView.getEditText().getText().toString();
        String password = passwordInputView.getEditText().getText().toString();
        String birthDate = dateInputView.getEditText().getText().toString();
        String medical = medicalInputView.getEditText().getText().toString();
        String plate = plateInputView.getEditText().getText().toString();
        String phone = phoneInputView.getEditText().getText().toString();

        if (medicButton.isPressed()) {
            type = Constants.FIREBASE_USER_TYPE_MEDIC;
        } else if (patientButton.isPressed()) {
            type = Constants.FIREBASE_USER_TYPE_PATIENT;
        }

        this.toggleKeyboard();

        mActionListener.registerUser(type, name, email, password, birthDate, medical, plate, phone);
    }

    @Override
    public void showSuccess() {
        new MaterialDialog.Builder(this)
                .title(R.string.user_register_title)
                .content(R.string.user_register_success_content)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        openMain();
                    }
                })
                .positiveText(R.string.user_register_success_positive)
                .autoDismiss(false)
                .show();
    }

    @Override
    public void showError() {
        Snackbar.make(toolbar, getString(R.string.user_register_incomplete_fields), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        // Kill activity
        this.finish();
    }

    @Override
    public void toggleKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
    }

    @Override
    public void setUserActionListener(RegisterContract.UserActionsListener listener) {
        mActionListener = listener;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
