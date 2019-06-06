package com.cccdlabs.sample.seeder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cccdlabs.sample.presentation.ui.activities.MainActivity;
import com.cccdlabs.sample.seeder.executor.AppExecutors;
import com.cccdlabs.sample.seeder.model.Populate;
import com.cccdlabs.sample.R;

import java.util.List;

public class SeederActivity extends AppCompatActivity {

    private static final String TAG = SeederActivity.class.getSimpleName();
    private AppExecutors mExecutor;
    private TextView mResultsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeder);
        SeederActivity.this.setTitle("Populate Sample Database");
        mExecutor = AppExecutors.getInstance();
        mResultsView = findViewById(R.id.tv_results);
        populate();
    }

    private void populate() {
        final Populate pop = new Populate(this);

        mExecutor.threadExecute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Clear database here
                    pop.clear();

                    List<String> items = pop.gizmos();
                    updateResults(items);
                    Log.v(TAG, items.toString());

                    items = pop.widgets();
                    updateResults(items);
                    Log.v(TAG, items.toString());

                    items = pop.doodads();
                    updateResults(items);
                    Log.v(TAG, items.toString());

                    //close db
                    //pop.close();

                    mExecutor.mainExecute(new Runnable() {
                        @Override
                        public void run() {
                            exit();
                        }
                    });
                } catch (final Exception e) {
                    Log.e(TAG, "populate()");
                    Log.e(TAG, e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateResults(final List<String> items) {
        if (items == null || items.size() == 0) {
            return;
        }

        mExecutor.mainExecute(new Runnable() {
            @Override
            public void run() {
                String results = mResultsView.getText().toString();
                for (String result : items) {
                    results += result + "\n";
                    mResultsView.setText(results);
                }
            }
        });
    }

    private void exit() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }
}
