package com.oscarrrweb.tddboilerplate.presentation.executor;

import com.oscarrrweb.tddboilerplate.domain.executor.MainThread;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class TestUiThread implements MainThread {
    @Override
    public Scheduler getScheduler() {
        return new TestScheduler();
    }
}
