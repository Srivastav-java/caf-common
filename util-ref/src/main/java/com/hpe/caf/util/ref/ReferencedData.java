/*
 * Copyright 2015-2023 Open Text.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hpe.caf.util.ref;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

/**
 * Utility wrapper for allowing data to potentially be within a message or located on a remote DataStore. The acquire(ObjectSource) method
 * allows transparent method of obtaining an InputStream to the data.
 *
 * This is primarily intended for use with large amounts of binary data that needs to be processed without storing it all in memory.
 */
public class ReferencedData
{
    private String reference;
    private byte[] data;

    // for deserialisation
    ReferencedData()
    {
    }

    /**
     * Create a new ReferencedData object.
     *
     * @param ref the optional reference to be interpreted which points to the object
     * @param data the optional actual data itself
     */
    ReferencedData(final String ref, final byte[] data)
    {
        this.reference = ref;
        this.data = data;
    }

    /**
     * @return the remote reference to the object in the DataStore, if set
     */
    public String getReference()
    {
        return reference;
    }

    public byte[] getData()
    {
        return data;
    }

    /**
     * Return the referenced data as a stream, potentially performing a remote lookup.
     *
     * @param source the implementation that provides object instances given the provided references
     * @return a stream of the data that this container is wrapping
     * @throws DataSourceException if the data cannot be acquired
     * @throws IllegalStateException if there is no object or reference present
     */
    public synchronized InputStream acquire(final DataSource source)
        throws DataSourceException
    {
        InputStream ret;
        if (data == null) {
            if (getReference() == null) {
                throw new IllegalStateException("No data or reference present");
            } else {
                ret = source.getStream(getReference());
            }
        } else {
            ret = new ByteArrayInputStream(data);
        }
        return ret;
    }

    /**
     * Determine the size of the data.
     *
     * @param source the implementation that provides object instances given the provided references
     * @return the size of the abstracted data, in bytes
     * @throws DataSourceException if the size information cannot be acquired
     */
    public synchronized long size(final DataSource source)
        throws DataSourceException
    {
        if (data == null) {
            if (getReference() == null) {
                throw new IllegalStateException("No data or reference present");
            } else {
                return source.getDataSize(getReference());
            }
        } else {
            return data.length;
        }
    }

    /**
     * Create a ReferencedData object that uses a remote reference to data present in an ObjectSource.
     *
     * @param ref the reference to be interpreted by the DataStore
     * @return a new ReferencedData instance that relates to data in an ObjectSource via reference
     */
    public static ReferencedData getReferencedData(final String ref)
    {
        return new ReferencedData(ref, null);
    }

    /**
     * Create a ReferencedData instance that directly wraps data without a reference.
     *
     * @param data the raw data to wrapper
     * @return a new ReferencedData instance that directly wraps the data specified
     */
    public static ReferencedData getWrappedData(final byte[] data)
    {
        return new ReferencedData(null, data);
    }

    /**
     * Create a ReferencedData instance that wraps data but also has a reference.
     *
     * @param ref the reference to be interpreted by the DataStore
     * @param data the raw data to wrapper, also accessible via the supplied reference
     * @return a new ReferencedData instance that wraps and references the data supplied
     */
    public static ReferencedData getWrappedData(final String ref, final byte[] data)
    {
        return new ReferencedData(Objects.requireNonNull(ref), data);
    }
}
