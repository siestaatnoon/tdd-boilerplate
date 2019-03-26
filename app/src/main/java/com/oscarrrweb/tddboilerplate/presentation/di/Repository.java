package com.oscarrrweb.tddboilerplate.presentation.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A scoping annotation for data repository.
 */
@Scope
@Retention(RUNTIME)
public @interface Repository {
}
