package com.hp.caf.api.worker;


import com.hp.caf.api.ConfigurationSource;


/**
 * Boilerplate for retrieving a WorkerQueue implementation.
 */
public interface WorkerQueueProvider
{
    /**
     * Create a new WorkerQueue instance.
     * @param configurationSource used for configuring the WorkerQueue
     * @param maxTasks the maximum number of tasks the worker can perform at once
     * @return a new WorkerQueue instance
     * @throws QueueException if a WorkerQueue could not be created
     */
    WorkerQueue getWorkerQueue(final ConfigurationSource configurationSource, final int maxTasks)
        throws QueueException;
}
