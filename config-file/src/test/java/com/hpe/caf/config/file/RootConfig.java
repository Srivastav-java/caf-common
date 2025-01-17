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
package com.hpe.caf.config.file;

import com.hpe.caf.api.Configuration;

import javax.validation.Valid;

public class RootConfig
{
    private String testString = "test";
    @Configuration
    @Valid
    private InnerConfig innerConfig;

    public String getTestString()
    {
        return testString;
    }

    public void setTestString(final String testString)
    {
        this.testString = testString;
    }

    public InnerConfig getInnerConfig()
    {
        return innerConfig;
    }

    public void setInnerConfig(final InnerConfig innerConfig)
    {
        this.innerConfig = innerConfig;
    }
}
