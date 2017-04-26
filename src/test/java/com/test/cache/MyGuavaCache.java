package com.test.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by songyigui on 2016/11/16.
 */
public class MyGuavaCache<K, V> {
    private static Logger log = LoggerFactory.getLogger(MyGuavaCache.class);

    private Cache<K, V> cache;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public MyGuavaCache() {
        cache = CacheBuilder.newBuilder().build();
    }

    public MyGuavaCache(long maxSize, long expireTime, long refreshTime, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireTime, timeUnit)
                .refreshAfterWrite(refreshTime, timeUnit)
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
                        log.info(removalNotification.getKey() + " was removed,cause is" + removalNotification.getCause());
                    }
                })
                .build();
    }

    /**
     * 通过key获取value，可返回null
     *
     * @param key
     * @return
     */
    public V getIfPresent(K key) {
        if (key == null) {
            return null;
        }
        return cache.getIfPresent(key);
    }

    /**
     * 通过key和callable获取value
     *
     * @param key
     * @param valueLoader
     * @return
     * @throws ExecutionException
     */
    public V get(K key, Callable<? extends V> valueLoader) throws ExecutionException {
        if (key == null || valueLoader == null) {
            return null;
        }
        return cache.get(key, valueLoader);
    }

    /**
     * 返回key和value的map视图
     *
     * @param keys
     * @return
     */
    public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
        if (keys == null) {
            return null;
        }
        return cache.getAllPresent(keys);
    }

    /**
     * 放入缓存
     *
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        if (key == null || value == null) {
            return;
        }
        cache.put(key, value);
    }

    /**
     * 批量放入缓存
     *
     * @param m
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m == null) {
            return;
        }
        cache.putAll(m);
    }

    /**
     * 使key对应缓存失效
     *
     * @param key
     */
    public void invalidate(K key) {
        if (key == null) {
            return;
        }
        cache.invalidate(key);
    }

    /**
     * 批量key对应失效缓存
     *
     * @param keys
     */
    public void invalidateAll(Iterable<?> keys) {
        if (keys == null) {
            return;
        }

        cache.invalidateAll(keys);
    }

    /**
     * 所有缓存失效
     */
    public void invalidateAll() {
        cache.invalidateAll();
    }

    /**
     * 缓存数量
     *
     * @return
     */
    public long size() {
        return cache.size();
    }

    /**
     * 缓存状态统计
     *
     * @return
     */
    public CacheStats stats() {
        return cache.stats();
    }

    /**
     * 返回ConcurrentMap
     *
     * @return
     */
    public ConcurrentMap<K, V> asMap() {
        return cache.asMap();
    }

    public void cleanUp() {
        cache.cleanUp();
    }


//    private static class GuavaCacheHolder {
//            private static MyGuavaCache instance = new MyGuavaCache();
//        }
//
//    public static MyGuavaCache getInstance() {
//        return GuavaCacheHolder.instance;
//    }

//
//    public MyGuavaCache(int maxSize, int expireTime, int refreshTime, TimeUnit timeUnit, CacheLoader<String, String> loader) {
//        this.maxSize = maxSize;
//        this.expireTime = expireTime;
//        this.refreshTime = refreshTime;
//        this.timeUnit = timeUnit;
//        this.loader = loader;
//    }

    //    new CacheLoader<String, String>(
//            ) {
//        @Override
//        public String load(String key) throws Exception {
//            return addcache(key);
//        }
//
//        @Override
//        public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
//            System.out.println("inside load all");
//            return addcacheAll(keys);
//        }
//
//        @Override
//        public ListenableFuture<String> reload(final String key, String oldString) throws Exception {
//            ListenableFutureTask<String> task = ListenableFutureTask.create(new Callable<String>() {
//                public String call() throws Exception {
//                    return load(key);
//                }
//            });
//
//            executor.execute(task);
//            return task;
//        }
//    }
//
//    private static class GuavaCacheHolder {
//        private static MyGuavaCache instance = new MyGuavaCache();
//    }
//
//    public static MyGuavaCache getInstance() {
//        return GuavaCacheHolder.instance;
//    }
//
//
//    private String addcache(String key) {
//        System.out.println("adding cache");
//        return key.toUpperCase();
//    }
//
//    private Map<String, String> addcacheAll(Iterable<? extends String> keys) {
//        Map<String, String> map = new HashMap<String, String>();
//        for (String s : keys) {
//            map.put(s, s.toUpperCase());
//        }
//        return map;
//    }
//
//    public String getEntry(String args) throws ExecutionException {
//        System.out.println(cache.size());
//        return cache.get(args);
//    }
//
//    public ImmutableMap<String, String> getEntryAll(String args) throws ExecutionException {
//        List<String> list = new ArrayList<String>();
//        list.add(args);
//        return cache.getAll(list);
//    }
//
//
//    public static void main(String[] args) {
//        MyGuavaCache gt = MyGuavaCache.getInstance();
//        try {
//            System.out.println(gt.getEntry("Suvendu"));
//            System.out.println(gt.getEntry("Suvendu"));
//            Thread.sleep(2100);
//            System.out.println(gt.getEntry("Suvendu"));
//            System.out.println(gt.getEntry("Suvendu"));
//
//            System.out.println(gt.getEntryAll("Suvendu1"));
//            System.out.println(gt.getEntry("Suvendu1"));
//
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//
//    LoadingCache<K, V> graphs = CacheBuilder.newBuilder()
//            .maximumSize(1000)
//            .expireAfterWrite(10, TimeUnit.MINUTES)
//            .removalListener(MY_LISTENER)
//            .build(
//                    new CacheLoader<String, Graph>() {
//                        public Graph load(String key) throws AnyException {
//                            return createExpensiveGraph(key);
//                        }
//                    });

//    try {
//        return graphs.get(key);
//    } catch (ExecutionException e) {
//        throw new OtherException(e.getCause());
//    }
//
//    return graphs.getUnchecked(key);
//
//    Cache<String, String> cache = CacheBuilder.newBuilder()
//            .maximumSize(1000)
//            .build(); // look Ma, no CacheLoader
//
//            try {
//        // If the key wasn't in the "easy to compute" group, we need to
//        // do things the hard way.
//        cache.get(key, new Callable<String>() {
//            @Override
//            public String call() throws AnyException {
//                return doThingsTheHardWay(key);
//            }
//        });
//    } catch (ExecutionException e) {
//        throw new OtherException(e.getCause());
//    }
//
//    cache.put(key, value)
//
//            Cache.invalidateAll()
//            Cache.invalidateAll()
//            Cache.invalidate(key)
//
//    CacheLoader<String, DatabaseConnection> loader = new CacheLoader<String, DatabaseConnection> () {
//        public DatabaseConnection load(String key) throws Exception {
//            return openConnection(key);
//        }
//    };
//    RemovalListener<String, DatabaseConnection> removalListener = new RemovalListener<String, DatabaseConnection>() {
//        public void onRemoval(RemovalNotification<String, DatabaseConnection> removal) {
//            DatabaseConnection conn = removal.getString();
//            conn.close(); // tear down properly
//        }
//    };
//
//    return CacheBuilder.newBuilder()
//            .expireAfterWrite(2, TimeUnit.MINUTES)
//    .removalListener(removalListener)
//    .build(loader);]
//            RemovalListeners.asynchronous(RemovalListener, Executor)
//
//
//    LoadingCache<String, Graph> graphs = CacheBuilder.newBuilder()
//            .maximumSize(1000)
//            .refreshAfterWrite(1, TimeUnit.MINUTES)
//            .build(
//                    new CacheLoader<String, Graph>() {
//                        public Graph load(String key) { // no checked exception
//                            return getGraphFromDatabase(key);
//                        }
//
//                        public ListenableFuture<Graph> reload(final String key, Graph prevGraph) {
//                            if (neverNeedsRefresh(key)) {
//                                return Futures.immediateFuture(prevGraph);
//                            } else {
//                                // asynchronous!
//                                ListenableFutureTask<Graph> task = ListenableFutureTask.create(new Callable<Graph>() {
//                                    public Graph call() {
//                                        return getGraphFromDatabase(key);
//                                    }
//                                });
//                                executor.execute(task);
//                                return task;
//                            }
//                        }
//                    });
//
//    LoadingCache.refresh(K)
//
//            CacheLoader.reload(K, V)
}
