package com.quanlychitieunhom.Login.UI.State;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.Login.Data.Repository.LoginCallback;
import com.quanlychitieunhom.Login.Data.Repository.LoginRepo;
import com.quanlychitieunhom.Login.Data.Repository.LoginRepoImpl;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<LoginViewState> loginViewState = new MutableLiveData<>();
    private final LoginRepo loginRepo = new LoginRepoImpl();

    public MutableLiveData<LoginViewState> getLoginViewState() {
        return loginViewState;
    }

    public void processLogin(LoginIntent loginIntent, LoginModel loginModel) {
        if(loginIntent == LoginIntent.CLICK_LOGIN_BUTTON) {
            login(loginModel);
        }
    }

    private void login(LoginModel loginModel) {
        loginViewState.postValue(new LoginViewState(loginModel, LoginState.SENDING, "", "", "", ""));
        loginRepo.login(loginModel, new LoginCallback() {
            @Override
            public void onApiResponse(String message, String username, String token, String refreshToken) {
                if(message.equals("Success")) {
                    loginViewState.postValue(new LoginViewState(loginModel, LoginState.SUCCESS, "", token, username, refreshToken));
                } else {
                    loginViewState.postValue(new LoginViewState(loginModel, LoginState.ERROR, message, "", "", ""));
                }
            }
        });
    }
}
