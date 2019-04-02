package com.oscarrrweb.tddboilerplate.presentation.presenters.base;

import com.oscarrrweb.tddboilerplate.domain.repository.base.Repository;
import com.oscarrrweb.tddboilerplate.presentation.views.base.BaseView;

public abstract class AbstractPresenter<M> implements Presenter {

    private Repository<M> mRepository;
    private BaseView mView;

    public AbstractPresenter(final Repository<M> repository, final BaseView view) {
        mRepository = repository;
        mView = view;
    }

    protected Repository<M> getRepository() {
        return mRepository;
    }

    protected BaseView getView() {
        return mView;
    }
}
