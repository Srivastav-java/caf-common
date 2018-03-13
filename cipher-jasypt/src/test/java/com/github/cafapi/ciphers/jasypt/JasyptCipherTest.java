/*
 * Copyright 2015-2017 EntIT Software LLC, a Micro Focus company.
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
package com.github.cafapi.ciphers.jasypt;

import com.github.cafapi.BootstrapConfiguration;
import com.github.cafapi.Cipher;
import com.github.cafapi.CipherException;
import com.github.cafapi.ConfigurationException;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JasyptCipherTest
{
    private static final String PASS = "test123";

    @Test
    public void jasyptStringTest()
        throws ConfigurationException, CipherException
    {
        BootstrapConfiguration boot = Mockito.mock(BootstrapConfiguration.class);
        Mockito.when(boot.getConfiguration(JasyptCipher.CONFIG_SECURITY_PASS)).thenReturn(PASS);
        Cipher sp = new JasyptCipher(boot);
        String testString = "test456";
        Assert.assertEquals(testString, sp.decrypt(sp.encrypt(testString)));
    }

    @Test(expectedExceptions = CipherException.class)
    public void jasyptExceptionTest()
        throws ConfigurationException, CipherException
    {
        BootstrapConfiguration boot = Mockito.mock(BootstrapConfiguration.class);
        Mockito.when(boot.getConfiguration(JasyptCipher.CONFIG_SECURITY_PASS)).thenThrow(ConfigurationException.class);
        Cipher sp = new JasyptCipher(boot);
        String testString = "test456";
        sp.decrypt(sp.encrypt(testString));
    }
}