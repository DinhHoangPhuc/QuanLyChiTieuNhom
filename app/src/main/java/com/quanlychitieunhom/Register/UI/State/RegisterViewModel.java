package com.quanlychitieunhom.Register.UI.State;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.Register.Data.Repository.RegisterCallback;
import com.quanlychitieunhom.Register.Data.Repository.RegisterRepo;
import com.quanlychitieunhom.Register.Data.Repository.RegisterRepoImpl;

import java.util.Map;

public class RegisterViewModel extends ViewModel {
    private final MutableLiveData<RegisterViewState> registrationModel = new MutableLiveData<>();
    private final RegisterRepo registerRepo = new RegisterRepoImpl();

    public LiveData<RegisterViewState> getRegistrationModel() {
        return registrationModel;
    }

    public void proccessIntent(RegisterIntent intent, RegisterModel registerModel) {
        if(intent == RegisterIntent.CLICK_REGISTER_BUTTON) {
            register(registerModel);
        }
    }

    private void register(RegisterModel registerModel) {
        registrationModel.postValue(new RegisterViewState(RegisterState.SENDING, registerModel, null));
        registerRepo.register(registerModel, new RegisterCallback() {
            @Override
            public void onApiResponse(Map<String, String> response, int statusCode) {
//                if(message.equals("Success")) {
//                    registrationModel.postValue(new RegisterViewState(RegisterState.SUCCESS, registerModel, ""));
//                } else {
//                    registrationModel.postValue(new RegisterViewState(RegisterState.ERROR, registerModel, message));
//                }

                if(statusCode == 201) {
                    registrationModel.postValue(new RegisterViewState(RegisterState.SUCCESS, registerModel, response));
                } else if(statusCode == 400) {
                    registrationModel.postValue(new RegisterViewState(RegisterState.ERROR_VALIDATION, registerModel, response));
                } else {
                    registrationModel.postValue(new RegisterViewState(RegisterState.ERROR, registerModel, response));
                }
            }
        });
    }
}
