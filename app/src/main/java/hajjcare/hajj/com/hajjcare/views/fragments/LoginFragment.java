package hajjcare.hajj.com.hajjcare.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import hajjcare.hajj.com.hajjcare.R;
import hajjcare.hajj.com.hajjcare.control.Controller;
import hajjcare.hajj.com.hajjcare.control.HajjCareApplicationClass;
import hajjcare.hajj.com.hajjcare.control.UIController;
import hajjcare.hajj.com.hajjcare.models.LoginRequest;
import hajjcare.hajj.com.hajjcare.models.LoginResponse;
import hajjcare.hajj.com.hajjcare.networklayer.OnCallComplete;
import hajjcare.hajj.com.hajjcare.views.activity.HomeActivity;

/**
 * Created by amra on 2/3/2018.
 */


public class LoginFragment extends BaseFragment {

    private View containerView;
    private Button loginButton;
    private EditText email;
    private EditText password;
    private TextView createAccountTextView;
    private TextView arabic;
    private TextView english;
    private CheckBox rememberMe;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText passport;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        containerView = inflater.inflate(R.layout.fragment_login, container, false);
        initUI(containerView);


        return containerView;
    }

    private void initUI(@Nullable View container) {
        email = (EditText) containerView.findViewById(R.id.email_edit_text);
        password = (EditText) containerView.findViewById(R.id.password_edit_text);
        passport = (EditText) containerView.findViewById(R.id.passport_edit_text);
        loginButton = (Button) containerView.findViewById(R.id.registerButton);
        rememberMe = (CheckBox) containerView.findViewById(R.id.remember_me);
        preferences = getActivity().getSharedPreferences("star_smiles", Context.MODE_PRIVATE);

        if (preferences.getString("user", null) != null) {
            rememberMe.setChecked(true);
            email.setText(preferences.getString("user", null));
            password.setText(preferences.getString("pass", null));

        }





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateAndLogin();
            }
        });
    }

    private void validateAndLogin() {
        final String passportTxt = String.valueOf(passport.getText());
//        final String passwordTxt = String.valueOf(password.getText());
//        if (emailTxt != null && !emailTxt.isEmpty() && passwordTxt != null && !emailTxt.isEmpty()) {

//            LoginRequest loginRequest = new LoginRequest(emailTxt, passwordTxt);

//            Controller.getInstance().login(new OnCallComplete() {
//                @Override
//                public void onSuccess(Object object) {
//
////                    StarSmilesApplicationClass.setToken(((LoginResponse) object).getToken());
//                    if (rememberMe.isChecked()) {
//                        editor = preferences.edit();
//                        editor.putString("user", emailTxt);
//                        editor.putString("pass", passwordTxt);
//                        editor.putString("token", ((LoginResponse) object).getToken());
//                        editor.commit();
//                    }else{
//                        editor = preferences.edit();
//                        editor.putString("user", null);
//                        editor.putString("pass", null);
//                        editor.putString("token", null);
//                        editor.commit();
//                    }
//                    Intent intent = new Intent(getActivity(), HomeActivity.class);
//                    startActivity(intent);
////                    getActivity().finish();
//                }

//                @Override
//                public void onFailure(Exception appException) {
//
//                    UIController.handleActivtyResponseError(appException, getActivity(), false, true);
//                }
//            }, loginRequest);
//        } else if (emailTxt == null || emailTxt.isEmpty()) {
//            email.setError(getString(R.string.login_user_error));
//        } else {
//
//            password.setError(getString(R.string.login_password_error));
//        }

        if(passport != null && !passport.getText().toString().isEmpty()){

            LoginRequest loginRequest = new LoginRequest(passportTxt);

            Controller.getInstance().login(new OnCallComplete() {
                @Override
                public void onSuccess(Object object) {

                    if(((LoginResponse)object).getFind().equals("true")){
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        Controller.setMyId(passportTxt);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(), getString(R.string.login_password_error), Toast.LENGTH_SHORT).show();
                    }

//                    getActivity().finish();
                }

                @Override
                public void onFailure(Exception appException) {

                    UIController.handleActivtyResponseError(appException, getActivity(), false, true);
                }
            }, loginRequest);
        }
    }


}

