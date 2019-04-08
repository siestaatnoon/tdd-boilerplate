package com.oscarrrweb.sandbox.seeder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oscarrrweb.sandbox.R;
import com.oscarrrweb.sandbox.executor.AppExecutors;
import com.oscarrrweb.sandbox.presentation.ui.activities.MainActivity;
import com.oscarrrweb.sandbox.seeder.model.Populate;
import com.oscarrrweb.sandbox.seeder.model.Update;

import java.util.List;

public class SeederActivity extends AppCompatActivity {

    private static final String TAG = com.oscarrrweb.sandbox.seeder.SeederActivity.class.getSimpleName();
    private AppExecutors mExecutor;
    private TextView mResultsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpop);
        com.oscarrrweb.sandbox.seeder.SeederActivity.this.setTitle("Populate Database");
        mExecutor = AppExecutors.getInstance();
        mResultsView = (TextView) findViewById(R.id.tv_results);

        Button yesButton = (Button) findViewById(R.id.button_yes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populate();
            }
        });

        Button noButton = (Button) findViewById(R.id.button_no);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        Button updateButton = (Button) findViewById(R.id.button_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        Button closeButton = (Button) findViewById(R.id.button_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }

    private void clear() {
        final Populate pop = new Populate(this);
        mExecutor.threadExecute(new Runnable() {
            @Override
            public void run() {
                try {
                    pop.clear();
                } catch (final Throwable throwable) {
                    Log.e(TAG, "clear()");
                    Log.e(TAG, throwable.getMessage());
                }
            }
        });
    }

    private void populate() {
        final Populate pop = new Populate(this);
        findViewById(R.id.initial_screen).setVisibility(View.GONE);
        findViewById(R.id.result_screen).setVisibility(View.VISIBLE);

        mExecutor.threadExecute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Clear database here
                    pop.clear();

                    List<String> items = pop.tightsBrand();
                    updateResults(items);
                    Log.v(TAG, items.toString());

                    items = pop.tightsStore();
                    updateResults(items);
                    Log.v(TAG, items.toString());

                    items = pop.tights();
                    updateResults(items);
                    Log.v(TAG, items.toString());

                    items = pop.tightsJournal();
                    updateResults(items);
                    Log.v(TAG, items.toString());
                } catch (final Throwable throwable) {
                    Log.e(TAG, "populate()");
                    Log.e(TAG, throwable.getMessage());
                }
            }
        });
    }

    private void update() {
        final Update update = new Update(this);
        findViewById(R.id.button_update).setEnabled(false);

        mExecutor.threadExecute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Clear database here
                    List<String> items = update.perform();
                    updateResults(items);
                    Log.v(TAG, items.toString());

                    mExecutor.mainExecute(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.button_update).setVisibility(View.GONE);
                        }
                    });
                } catch (final Throwable throwable) {
                    Log.e(TAG, "update()");
                    Log.e(TAG, throwable.getMessage());
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
        // SyncUtils.forceSync(getBaseContext());
        startActivity(intent);
    }
}
