package com.yuwenyun.demos.flink.cep;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-11-29 18:24
 */
public class AdvertiserInfo {

    private long advertiserId;
    private int showCnt;

    public AdvertiserInfo() {
    }

    public long getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(long advertiserId) {
        this.advertiserId = advertiserId;
    }

    public int getShowCnt() {
        return showCnt;
    }

    public void setShowCnt(int showCnt) {
        this.showCnt = showCnt;
    }
}
