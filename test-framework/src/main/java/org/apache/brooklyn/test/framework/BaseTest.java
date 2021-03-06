/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.brooklyn.test.framework;

import java.util.Map;

import org.apache.brooklyn.config.ConfigKey;
import org.apache.brooklyn.core.config.ConfigKeys;
import org.apache.brooklyn.core.entity.trait.Startable;
import org.apache.brooklyn.util.time.Duration;

import com.google.common.collect.ImmutableList;

/**
 * A base interface for all tests.
 */
public interface BaseTest extends TargetableTestComponent, Startable {

    /**
     * The assertions to be made.
     */
    ConfigKey<Object> ASSERTIONS = ConfigKeys.newConfigKey(Object.class, "assert", "Assertions to be evaluated",
        ImmutableList.<Map<String, Object>>of());

    /**
     * The duration to wait for an assertion to succeed or fail before throwing an exception.
     */
    ConfigKey<Duration> TIMEOUT = ConfigKeys.newConfigKey(Duration.class, "timeout", "Time to wait on result",
        Duration.seconds(1));

    /**
     * The max number of iterations to run before throwing an exception.
     */
    ConfigKey<Integer> ITERATION_LIMIT = ConfigKeys.newConfigKey(Integer.class, "iterations", "Max iterations to try to get result",
        Integer.MAX_VALUE);

    /**
     * Max time between checking the assertion (exponential backoff to this limit).
     */
    ConfigKey<Duration> BACKOFF_TO_PERIOD = ConfigKeys.newConfigKey(Duration.class, "backoffToPeriod", 
            "Max time between checking the assertion (exponential backoff to this limit)",
            Duration.millis(500));

}
