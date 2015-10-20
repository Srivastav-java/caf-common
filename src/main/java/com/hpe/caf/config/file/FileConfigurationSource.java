package com.hpe.caf.config.file;


import com.hpe.caf.api.BootstrapConfiguration;
import com.hpe.caf.api.CafConfigurationSource;
import com.hpe.caf.api.Cipher;
import com.hpe.caf.api.Codec;
import com.hpe.caf.api.ConfigurationException;
import com.hpe.caf.api.HealthResult;
import com.hpe.caf.naming.Name;
import com.hpe.caf.naming.ServicePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * This is a ConfigurationProvider that reads from a local disk file.
 * If the bootstrap parameter config.path is set, it will read the config
 * files from the specified directory, otherwise it will use the working dir.
 *
 * If retrieving the configuration class "TestConfiguration", using the
 * ServicePath /a/b, it will expect the file to be called "cfg_a_b_TestConfiguration".
 */
public class FileConfigurationSource extends CafConfigurationSource
{
    public static final String CONFIG_PATH = "CAF_CONFIG_PATH";
    @Deprecated
    public static final String OLD_CONFIG_PATH = "config.path";
    private Path configPath;
    private static final Logger LOG = LoggerFactory.getLogger(FileConfigurationSource.class);


    /**
     * {@inheritDoc}
     */
    public FileConfigurationSource(final BootstrapConfiguration bootstrap, final Cipher cipher, final ServicePath servicePath, final Codec codec)
            throws ConfigurationException
    {
        super(bootstrap, cipher, servicePath, codec);
        try {
            configPath = FileSystems.getDefault().getPath(getConfigPath(bootstrap));
        } catch (InvalidPathException e) {
            throw new ConfigurationException("Invalid configuration path", e);
        }
        LOG.debug("Initialised");
    }


    @Override
    public void shutdown()
    {
        // nothing to do
    }


    @Override
    public HealthResult healthCheck()
    {
        return HealthResult.RESULT_HEALTHY;
    }


    @Override
    protected InputStream getConfigurationStream(final Class configClass, final Name relativePath)
            throws ConfigurationException
    {
        String configFile = nameToFile(configClass, relativePath);
        Path p;
        if ( configPath != null ) {
            p = configPath.resolve(configFile);
        } else {
            p = Paths.get(configFile);
        }
        LOG.debug("Getting configuration for {} from {}", configClass.getSimpleName(), p);
        try {
            return Files.newInputStream(p);
        } catch (IOException e) {
            throw new ConfigurationException("Cannot read config file: " + configFile, e);
        }
    }


    /**
     * Convert a (partial) ServicePath into a file name to access. The file names are
     * in the format "cfg_group_subgroup_appid_ConfigurationClass".
     * @param configClass the configuration class to try and acquire
     * @param servicePath the partial or complete ServicePath in Name format
     * @return the constructed file name to try and access
     */
    private String nameToFile(final Class configClass, final Name servicePath)
    {
        StringBuilder builder = new StringBuilder("cfg");
        for(String component : servicePath) {
            builder.append("_").append(component);
        }
        builder.append("_").append(configClass.getSimpleName());
        return builder.toString();
    }


    private String getConfigPath(final BootstrapConfiguration bootstrap)
        throws ConfigurationException
    {
        String ret;
        if ( bootstrap.isConfigurationPresent(CONFIG_PATH) ) {
            ret = bootstrap.getConfiguration(CONFIG_PATH);
        } else if ( bootstrap.isConfigurationPresent(OLD_CONFIG_PATH) ) {
            ret = bootstrap.getConfiguration(OLD_CONFIG_PATH);
        } else {
            throw new ConfigurationException("Configuration parameter " + CONFIG_PATH + " not present");
        }
        return ret;
    }
}
