package com.oscarrrweb.tddboilerplate.presentation.presenters;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.repository.sample.GizmoRepository;
import com.oscarrrweb.tddboilerplate.domain.executor.ExecutorThread;
import com.oscarrrweb.tddboilerplate.domain.executor.MainThread;
import com.oscarrrweb.tddboilerplate.domain.interactors.sample.SampleDisplayUseCase;
import com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo;
import com.oscarrrweb.tddboilerplate.presentation.mappers.sample.GizmoUiModelMapper;
import com.oscarrrweb.tddboilerplate.presentation.model.sample.GizmoUiModel;
import com.oscarrrweb.tddboilerplate.presentation.presenters.base.AbstractPresenter;
import com.oscarrrweb.tddboilerplate.presentation.presenters.observers.PresenterObserver;
import com.oscarrrweb.tddboilerplate.presentation.views.MainView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class MainPresenter extends AbstractPresenter<Gizmo> {

    @Inject SampleDisplayUseCase mUseCase;
    @Inject ExecutorThread mExecutorThread;
    @Inject MainThread mMainThread;
    @Inject Context mContext;

    class ShowSampleDisplayObserver extends PresenterObserver<List<GizmoUiModel>> {

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
