package com.quanlychitieunhom.Register.UI.State;

import java.util.Map;

public class RegisterViewState {
    private final RegisterState registerState;
    private final RegisterModel registrationModel;
    private final Map<String, String> repsonseObject;

    public RegisterViewState(RegisterState registerState, RegisterModel registrationModel, Map<String, String> repsonseObject) {
        this.registerState = registerState;
        this.registrationModel = registrationModel;
        this.repsonseObject = repsonseObject;
    }

    public RegisterState getRegisterState() {
        return registerState;
    }

    public RegisterModel getRegistrationModel() {
        return registrationModel;
    }

    public Map<String, String> getRepsonseObject() {
        return repsonseObject;
    }
}
