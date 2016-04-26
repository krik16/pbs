package com.shouyingbao.pbs.redis;

import redis.clients.jedis.ShardedJedis;

/**
 *  redisSource
 *  kejun
 **/
public interface RedisDataSource {

    public abstract ShardedJedis getRedisClient();

    public void returnResource(ShardedJedis shardedJedis);


    //返回资源
    public void returnResource(ShardedJedis shardedJedis, boolean broken);
}
