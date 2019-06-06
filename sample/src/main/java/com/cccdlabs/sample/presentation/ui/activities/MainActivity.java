package com.cccdlabs.sample.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cccdlabs.sample.presentation.di.components.MainComponent;
import com.cccdlabs.sample.presentation.di.components.SampleAppComponent;
import com.cccdlabs.sample.presentation.di.modules.DataModule;
import com.cccdlabs.sample.presentation.model.GizmoUiModel;
import com.cccdlabs.sample.presentation.ui.adapters.SampleAdapter;
import com.cccdlabs.sample.R;
import com.cccdlabs.sample.presentation.di.components.DaggerMainComponent;
import com.cccdlabs.sample.presentation.di.components.DaggerSampleAppComponent;
import com.cccdlabs.sample.presentation.presenters.MainPresenter;
import com.cccdlabs.sample.presentation.views.MainView;
import com.cccdlabs.tddboilerplate.App;
import com.cccdlabs.tddboilerplate.presentation.di.HasComponent;
import com.cccdlabs.tddboilerplate.presentation.di.modules.AppModule;
import com.cccdlabs.tddboilerplate.presentation.ui.activities.base.BaseAppCompatActivity;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends BaseAppCompatActivity implements MainView, HasComponent<MainComponent> {

    @Inject MainPresenter mPresenter;
    @Inject
    SampleAdapter mAdapter;

    private MainComponent mMainComponent;
    private Context mContext;

    /**
     * TODO: Convert to Butterknife
     */
    //@BindView(R.id.gizmo_list)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Convert to Butterknife
        // ButterKnife.bind(this);

        // Typically we can access the AppComponent since this
        // Activity extends BaseAppCompatActivity as below
        //
        // AppComponent appComponent = getAppComponent();
        //
        // However, since our sample is in another module, we're
        // access the SampleAppComponent instead
        //
        SampleAppComponent appComponent = DaggerSampleAppComponent.builder()
                .appModule(new AppModule((App) getApplication()))
                .dataModule(new DataModule(this))
                .build();

        appComponent.inject(appComponent.widgetMapper());
        appComponent.inject(appComponent.gizmoMapper());
        appComponent.inject(appComponent.widgetRepository());
        appComponent.inject(appComponent.gizmoRepository());

        mMainComponent = DaggerMainComponent.builder()
                .sampleAppComponent(appComponent)
                .activityModule(getActivityModule())
                .build();

        mMainComponent.inject(this);
        mMainComponent.inject(mPresenter);
        mMainComponent.inject(mMainComponent.sampleDisplayUseCase());
        mContext = appComponent.context();

        // setup recycler view
        mRecyclerView = findViewById(R.id.gizmo_list); // TODO: Remove for Butterknife
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putBoolean(STATE_DIALOG, mDialog != null && mDialog.isShowing());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        final Intent intent;

        switch (id) {
            case R.id.action_about:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_close:
                finish();
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showGizmos(List<GizmoUiModel> items) {
        mAdapter.addItems(items);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        Timber.e(message);
        showMessage(message);
    }

    @Override
    public Context context() {
        return mContext;
    }

    @Override
    public MainComponent getComponent() {
        return mMainComponent;
    }

    protected void showMessage(String message) {
        if ( ! TextUtils.isEmpty(message)) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }
}
