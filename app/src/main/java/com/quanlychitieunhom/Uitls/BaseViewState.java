package com.quanlychitieunhom.Uitls;

public class BaseViewState<T> {
    private T data;
    private StateUtil stateUtil;

    public BaseViewState(T data, StateUtil stateUtil) {
        this.data = data;
        this.stateUtil = stateUtil;
    }

    public T getData() {
        return data;
    }

    public StateUtil getStateUtil() {
        return stateUtil;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStateUtil(StateUtil stateUtil) {
        this.stateUtil = stateUtil;
    }
}
