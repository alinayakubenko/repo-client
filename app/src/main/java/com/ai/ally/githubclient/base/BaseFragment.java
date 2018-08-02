package com.ai.ally.githubclient.base;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPresenter().setView(this);
        getPresenter().onViewCreated();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @NonNull
    public abstract BasePresenter getPresenter();
}
