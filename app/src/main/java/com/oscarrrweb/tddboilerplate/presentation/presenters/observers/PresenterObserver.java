package com.oscarrrweb.tddboilerplate.presentation.presenters.observers;

import com.oscarrrweb.tddboilerplate.presentation.presenters.base.Presenter;

import io.reactivex.observers.DisposableSingleObserver;

abstract public class PresenterObserver<T> extends DisposableSingleObserver<T> {

    private Presenter presenter;

    public PresenterObserver(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    abstract public void onSuccess(T param);

    @Override
    public void onError(final Throwable error) {
        presenter.onError(error.getMessage());
    }
}
