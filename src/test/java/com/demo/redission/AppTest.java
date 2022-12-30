package com.demo.redission;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {

        Config config = new Config();
        var clusterConfig  =  config.useSingleServer();
        // use "rediss://" for SSL connection
        clusterConfig.setAddress("redis://127.0.0.1:6379");
        clusterConfig.setPingConnectionInterval(0);
        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        redisson.getBucket("bucket", StringCodec.INSTANCE).set("aaa", 100 , TimeUnit.SECONDS );
        var res =  redisson.getBucket("bucket", StringCodec.INSTANCE).get() ;
        Assert.assertEquals("aaa" , res);
    }
}
