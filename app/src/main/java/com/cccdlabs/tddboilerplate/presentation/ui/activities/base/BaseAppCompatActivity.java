package com.cccdlabs.tddboilerplate.presentation.ui.activities.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cccdlabs.tddboilerplate.App;
import com.cccdlabs.tddboilerplate.R;
import com.cccdlabs.tddboilerplate.presentation.di.components.AppComponent;
import com.cccdlabs.tddboilerplate.presentation.di.modules.ActivityModule;
import com.cccdlabs.tddboilerplate.presentation.ui.utils.SnackbarUtils;

/**
 * Activity abstraction that extends {@link AppCompatActivity} and performs the dependency
 * injection for it. Also methods to retrieve the App DI component (for {@link android.content.Context}
 * and DI module.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
abstract public class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getAppComponent().inject(this);
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected void showToast(String message) {
        if ( ! TextUtils.isEmpty(message)) {
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    protected void showSnackbar(@NonNull View view, String message) {
        if ( ! TextUtils.isEmpty(message)) {
            SnackbarUtils.notice(this, view, message, getResources().getString(R.string.dismiss));
        }
    }
}
