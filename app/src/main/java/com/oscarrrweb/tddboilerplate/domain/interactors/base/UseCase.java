package com.oscarrrweb.tddboilerplate.data.interactors.base;

import io.reactivex.Single;

public interface UseCase<P, R> {

    Single<R> run(P parameter);

    Single<R> execute(P parameter);
}