package com.coop.android.presenter.base;

import android.content.Context;

import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends BaseView> implements Presenter<T> {

    private T view;
    public Context context;
    private Disposable disposable;


    public void setSubscription(Disposable disposable) {
        this.disposable = disposable;
    }

    public BasePresenter(Context context) {
        this.context = context;
    }


    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
//        if (subscription != null) {
//            subscription.unsubscribe();
//        }
//        this.view = null;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public T getView() {
        return view;
    }
}
