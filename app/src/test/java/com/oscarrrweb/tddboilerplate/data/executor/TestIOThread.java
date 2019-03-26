package com.oscarrrweb.tddboilerplate.data.executor;

import com.oscarrrweb.tddboilerplate.domain.executor.ExecutorThread;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class TestIOThread implements ExecutorThread {
    @Override
    public Scheduler getScheduler() {
        return new TestScheduler();
    }
}
