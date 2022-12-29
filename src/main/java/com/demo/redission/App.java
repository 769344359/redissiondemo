package com.demo.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Config config = new Config();
       var clusterConfig  =  config.useClusterServers();
               // use "rediss://" for SSL connection
        clusterConfig.addNodeAddress("redis://patpat-test.syykg2.clustercfg.apse1.cache.amazonaws.com:6379");
        clusterConfig.setPingConnectionInterval(0);
        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        redisson.getBucket("bucket", StringCodec.INSTANCE).set("aaa", 100 , TimeUnit.SECONDS );
        System.out.println("aaaaa");
    }
}
