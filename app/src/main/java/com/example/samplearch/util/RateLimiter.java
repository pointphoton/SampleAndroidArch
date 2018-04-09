package com.example.samplearch.util;

import android.os.SystemClock;
import android.support.v4.util.ArrayMap;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;


/**
 * Utility class that decides whether we should fetch some data or not.
 */
public class RateLimiter<KEY> {
    private ArrayMap<KEY, Long> timestamps = new ArrayMap<>();
    private final long timeout;

    public RateLimiter(int timeout, TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout);
        DebugLog.write("timeout -> ",timeout);
    }

    public synchronized boolean shouldFetch(KEY key) {
        DebugLog.write();
        Long lastFetched = timestamps.get(key);
        long now = now();
        if (lastFetched == null) {
            timestamps.put(key, now);
            return true;
        }
        if (now - lastFetched > timeout) {
            long l=now - lastFetched ;
            long t=timeout;
            DebugLog.write("time -> ", MessageFormat.format("now : {0} - lastFetched : {1} - timeout : {2} ",now,lastFetched,timeout));
            timestamps.put(key, now);
            return true;
        }
        return false;
    }

    private long now() {
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset(KEY key) {
        DebugLog.write();
        timestamps.remove(key);
    }
}
