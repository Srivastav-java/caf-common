package com.hp.caf.api.worker;


/**
 * Thrown by classes relevant to DataStore when it cannot handle a request.
 */
public class DataStoreException extends Exception
{
    public DataStoreException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
