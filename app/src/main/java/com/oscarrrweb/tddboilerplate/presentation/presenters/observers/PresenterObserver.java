package com.oscarrrweb.tddboilerplate.presentation.presenters.observers;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.presentation.exception.ErrorMessageFactory;
import com.oscarrrweb.tddboilerplate.presentation.presenters.base.Presenter;

import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

abstract public class PresenterObserver<T> extends DisposableSingleObserver<T> {

    private Presenter presenter;
    private Context context;

    public PresenterObserver(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    abstract public void onSuccess(T param);

    @Override
    public void onError(final Throwable throwable) {
        Timber.e(throwable);
        presenter.onError(ErrorMessageFactory.create(context, throwable));
    }
}
