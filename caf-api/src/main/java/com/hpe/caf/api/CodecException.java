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
package com.hpe.caf.api;

/**
 * Thrown when a Codec fails to encode or decode information.
 */
public class CodecException extends Exception
{
    /**
     * Create a new CodecException
     *
     * @param message information explaining the exception
     * @param cause the original cause of this exception
     */
    public CodecException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
