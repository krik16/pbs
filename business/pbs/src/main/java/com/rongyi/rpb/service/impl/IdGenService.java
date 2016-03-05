package com.rongyi.rpb.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 随机数生成
 * 测试8位ID 200W内不重复
 * 注意分布式环境下 需要传入不同的workerId
 * 后续考虑作为单独服务
 *
 * @author liuhao
 * @date 2015/7/22
 * @Description
 */
public class IdGenService {
    private final long workerId;
    private final static long twepoch = 1361753741828L;
    private long sequence = 0L;
    private final static long workerIdBits = 4L;
    public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
    private final static long sequenceBits = 10L;
    private final static long workerIdShift = sequenceBits;
    private final static long timestampLeftShift = sequenceBits + workerIdBits;
    public final static long sequenceMask = -1L ^ -1L << sequenceBits;
    private long lastTimestamp = -1L;

    public IdGenService(final long workerId) {
        this.workerId = workerId;
    }

    /**
     * @param length 小于16位
     * @return
     */
    public synchronized String nextId(int length) {
        long nextId = 0;
        long timestamp = this.timeGen();

        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }

        if (timestamp < this.lastTimestamp) {
            nextId = 0L;
        } else {
            this.lastTimestamp = timestamp;
            nextId = ((timestamp - twepoch << timestampLeftShift))
                    | (this.workerId << this.workerIdShift) | (this.sequence);
        }

        String nextIdStr = nextId + "";
        String id = String.format("%08d", nextId).substring(nextIdStr.length() - length, nextIdStr.length());
        return id;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) throws InterruptedException {
        Map<Object, Object> m1 = new HashMap<Object, Object>();
        Map<Object, Object> m2 = new HashMap<Object, Object>();
        IdGenService tdi1 = new IdGenService(2);
        IdGenService tdi2 = new IdGenService(3);
        T1 t1 = new T1(m1, "t1", (long) Math.random() * 10, tdi1);
        T1 t2 = new T1(m2, "t2", (long) Math.random() * 10, tdi2);
        Thread tt1 = new Thread(t1);
        Thread tt2 = new Thread(t2);
        tt1.start();
        tt2.start();
        tt1.join();
        tt2.join();
//        System.out.println("m1:");
//        workByKeySet(m1);
//        System.out.println("");
//        System.out.println("m2:");
//        workByKeySet(m2);
//        System.out.println("");
        System.out.println("diff start");
        diff(m1, m2);
    }


    static class T1 implements Runnable {
        String name;
        long seed;
        private Map<Object, Object> m1 = new HashMap<Object, Object>();
        IdGenService worker;

        public T1(Map<Object, Object> mm1, String name, long seed, IdGenService worker) {
            this.m1 = mm1;
            this.name = name;
            this.seed = seed;
            this.worker = worker;
        }

        @Override
        public void run() {
            int b = 0;
            Object a;
            System.out.println(name + " start:" + new Date().getTime());
            for (int i = 0; i < 2000000; i++) {
                a = worker.nextId(8);

//            System.out.println(a);
                if (m1.get(a) != null) {
//                    System.out.println(name + " xxxxx:" + a);
                    b++;
                } else {
                    m1.put(a, a);
                }
            }
            System.out.println(name + " end:" + b + " ||" + new Date().getTime());
        }
    }

    public static void workByKeySet(Map<Object, Object> map) {
        Set<Object> key = map.keySet();
        for (Iterator it = key.iterator(); it.hasNext(); ) {
            String s = (String) it.next();
            System.out.print(map.get(s) + "|");
        }
    }

    public static void diff(Map<Object, Object> map1, Map<Object, Object> map2) {
        int d = 0;
        Set<Object> key = map1.keySet();
        for (Iterator it = key.iterator(); it.hasNext(); ) {
            String s = (String) it.next();
            if (map2.get(s) != null) {
//               System.out.println(s);
                d++;
            }
        }
        System.out.println(d);
    }
}
