package com.oscarrrweb.tddboilerplate.domain.executor;

import com.oscarrrweb.tddboilerplate.domain.executor.base.ThreadScheduler;

/**
 * Thread abstraction for an execution thread for dealing with larger processes such as files
 * or network.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
public interface ComputationThread extends ThreadScheduler {}
