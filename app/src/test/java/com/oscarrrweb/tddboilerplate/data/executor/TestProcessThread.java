package com.oscarrrweb.tddboilerplate.data.executor;

import com.oscarrrweb.tddboilerplate.domain.executor.ComputationThread;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class TestProcessThread implements ComputationThread {

    @Override
    public Scheduler getScheduler() {
        return new TestScheduler();
    }
}
