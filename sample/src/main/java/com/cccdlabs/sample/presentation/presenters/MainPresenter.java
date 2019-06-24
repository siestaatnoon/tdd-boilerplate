package com.cccdlabs.sample.presentation.presenters;

import android.content.Context;

import com.cccdlabs.sample.data.repository.GizmoRepository;
import com.cccdlabs.sample.domain.interactors.SampleDisplayUseCase;
import com.cccdlabs.sample.domain.model.Gizmo;
import com.cccdlabs.sample.presentation.mappers.GizmoUiModelMapper;
import com.cccdlabs.sample.presentation.model.GizmoUiModel;
import com.cccdlabs.sample.presentation.views.MainView;
import com.cccdlabs.tddboilerplate.domain.executor.ExecutorThread;
import com.cccdlabs.tddboilerplate.domain.executor.MainThread;
import com.cccdlabs.tddboilerplate.presentation.presenters.base.AbstractPresenter;
import com.cccdlabs.tddboilerplate.presentation.presenters.observers.PresenterSingleObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class MainPresenter extends AbstractPresenter<Gizmo> {

    @Inject
    SampleDisplayUseCase mUseCase;
    @Inject ExecutorThread mExecutorThread;
    @Inject MainThread mMainThread;
    @Inject Context mContext;

    class ShowSampleDisplayObserver extends PresenterSingleObserver<List<GizmoUiModel>> {

        private ShowSampleDisplayObserver() {
            super(mContext, MainPresenter.this);
        }

        @Override
        public void onSuccess(final List<GizmoUiModel> items) {
            ((MainView) getView()).showGizmos(items);
        }
    }

    public MainPresenter(final GizmoRepository repository, final MainView view) {
        super(repository, view);
    }

    public void getAllGizmos() {
        mUseCase.execute(null)
                .map(new Function<List<Gizmo>, List<GizmoUiModel>>() {
                    @Override
                    public List<GizmoUiModel> apply(List<Gizmo> items) throws Exception {
                        return GizmoUiModelMapper.fromDomainModel(items);
                    }
                })
                .subscribeOn(mExecutorThread.getScheduler())
                .observeOn(mMainThread.getScheduler())
                .subscribe(getShowSampleDisplayObserver());
    }

    ShowSampleDisplayObserver getShowSampleDisplayObserver() {
        return new ShowSampleDisplayObserver();
    }

    @Override
    public void resume() {
        getAllGizmos();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(final String message) {
        getView().showError(message);
    }
}
