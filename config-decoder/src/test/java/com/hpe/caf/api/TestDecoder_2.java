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

import java.io.InputStream;

/**
 * An an implementation of Decoder for use with tests.
 */
public class TestDecoder_2 implements Decoder
{
    @Override
    public <T> T deserialise(InputStream stream, Class<T> clazz) throws CodecException
    {
        return null;
    }
}
