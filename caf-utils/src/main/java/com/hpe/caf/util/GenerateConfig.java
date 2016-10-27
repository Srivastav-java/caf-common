package com.hpe.caf.util;


import com.hpe.caf.api.Codec;
import com.hpe.caf.api.CodecException;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;


/**
 * Utility app for generating configuration files. It will use whichever implementation of Codec
 * is present on the classpath, and will output the result to stdout.
 *
 * Example usage:
 * java -cp "*" com.hpe.caf.util.GenerateConfig com.hp.caf.worker.queue.RabbitWorkerQueueConfiguration
 */
public final class GenerateConfig
{
    private GenerateConfig() { }


    public static void main(final String[] args)
        throws ClassNotFoundException, NoSuchMethodException, CodecException, InstantiationException, IllegalAccessException, InvocationTargetException, ModuleLoaderException
    {
        if ( args.length < 1 ) {
            System.err.println("Usage: java -cp * com.hpe.caf.util.GenerateConfig configClassName");
        } else {
            String className = args[0];
            try {
                Class clazz = Class.forName(className);
                Codec codec = ModuleLoader.getService(Codec.class);
                System.out.println(new String(codec.serialise(clazz.getConstructor().newInstance()), StandardCharsets.UTF_8));
            } catch (ClassNotFoundException e) {
                System.err.println("Class not found on classpath: " + className);
                throw e;
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | CodecException e) {
                System.err.println("Failed to generate configuration");
                throw e;
            } catch (ModuleLoaderException e) {
                System.err.println("Could not load codec component");
                throw e;
            }
        }
    }
}