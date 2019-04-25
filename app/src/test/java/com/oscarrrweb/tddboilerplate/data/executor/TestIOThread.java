package com.oscarrrweb.tddboilerplate.data.executor;

import com.oscarrrweb.tddboilerplate.domain.executor.ExecutorThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

@Singleton
public class TestIOThread implements ExecutorThread {

    @Inject
    public TestIOThread() {}

    @Override
    public Scheduler getScheduler() {
        return new TestScheduler();
    }
}
