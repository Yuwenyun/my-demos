package com.yuwenyun.demos.flink.streaming;

import javax.annotation.Nullable;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-03 11:52
 */
public class TimeLagWatermarkGenerator implements AssignerWithPeriodicWatermarks<String> {

    private long maxOutOfOrder = 5000;

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(System.currentTimeMillis() - maxOutOfOrder);
    }

    @Override
    public long extractTimestamp(String element, long previousElementTimestamp) {
        // 从数据中提取timestamp
        String[] elements = element.split(" ");
        long timestamp = Long.parseLong(elements[0]);
        return timestamp;
    }
}
