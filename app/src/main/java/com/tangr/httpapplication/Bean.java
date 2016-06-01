package com.tangr.httpapplication;

/**
 * Created by tangr on 16/6/1.
 */
public class Bean {

    /**
     * name : 0
     * timestamp : 1464795388910
     */

    private int name;
    private long timestamp;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return name+"..."+timestamp;
    }
}
