package com.demo.redission;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */

public class AppTest 
    extends TestCase
{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppTest.class);    /**
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

    // https://blog.csdn.net/usagoole/article/details/88024517


    public void testHeapByteBuf() {
        ByteBuf heapBuf = Unpooled.buffer(10);
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            //0,0
            log.info("offset:{},length:{}", offset, length);
        }
    }

    public void testDirectByteBuf() {
        ByteBuf directBuffer = Unpooled.directBuffer(10);
        if (!directBuffer.hasArray()) {
            int length = directBuffer.readableBytes();
            byte[] array = new byte[length];
            ByteBuf bytes = directBuffer.getBytes(directBuffer.readerIndex(), array);
            //0,0
            log.info("offset:{},length:{}", bytes.readerIndex(), array.length);
        }
    }
}
