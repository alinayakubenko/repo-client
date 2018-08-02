package com.ai.ally.githubclient.base;

public abstract class BasePresenter<View> {

    private View view;

    public abstract void onViewCreated();

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

}
