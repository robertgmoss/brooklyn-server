/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.brooklyn.api.mgmt;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import org.apache.brooklyn.api.entity.Entity;
import org.apache.brooklyn.util.guava.Maybe;

import com.google.common.annotations.Beta;

/**
 * This is a Brooklyn extension to the Java {@link Executor}.
 * 
 * The "context" could, for example, be an {@link Entity} so that tasks executed 
 * can be annotated as executing in that context.
 */
public interface ExecutionContext extends Executor {

    /**
     * Get the tasks executed through this context (returning an immutable set).
     */
    Set<Task<?>> getTasks();

    /**
     * See {@link ExecutionManager#submit(Map, TaskAdaptable)} for properties that can be passed in.
     */
    Task<?> submit(Map<?,?> properties, Runnable runnable);

    /**
     * See {@link ExecutionManager#submit(Map, TaskAdaptable)} for properties that can be passed in.
     */
    <T> Task<T> submit(Map<?,?> properties, Callable<T> callable);

    /** {@link ExecutionManager#submit(Runnable) */
    Task<?> submit(Runnable runnable);
 
    /** {@link ExecutionManager#submit(Callable) */
    <T> Task<T> submit(Callable<T> callable);

    /** See {@link ExecutionManager#submit(Map, TaskAdaptable)}. */
    <T> Task<T> submit(TaskAdaptable<T> task);
    
    /**
     * See {@link ExecutionManager#submit(Map, TaskAdaptable)} for properties that can be passed in.
     */
    <T> Task<T> submit(Map<?,?> properties, TaskAdaptable<T> task);

    boolean isShutdown();

    /**
     * Gets the value promptly, or returns {@link Maybe#absent()} if the value is not yet available.
     * It may throw an error if it cannot be determined whether a value is available immediately or not.
     * <p>
     * Implementations will typically attempt to execute in the current thread, with appropriate
     * tricks to make it look like it is in a sub-thread, and will attempt to be non-blocking but
     * if needed they may block.
     * <p>
     * Supports {@link Callable} and {@link Runnable} and some {@link Task} targets to be evaluated with "immediate" semantics.
     */
    // TODO reference ImmediateSupplier when that class is moved to utils project
    @Beta
    <T> Maybe<T> getImmediately(Object callableOrSupplierOrTask);

}