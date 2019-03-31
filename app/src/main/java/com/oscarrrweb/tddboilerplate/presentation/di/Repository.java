package com.oscarrrweb.tddboilerplate.presentation.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A scoping annotation for data {@link com.oscarrrweb.tddboilerplate.domain.repository.base.Repository}.
 * Lifetime should correspond to that of the {@link Presenter} and associated Activity
 */
@Scope
@Retention(RUNTIME)
public @interface Repository {
}
