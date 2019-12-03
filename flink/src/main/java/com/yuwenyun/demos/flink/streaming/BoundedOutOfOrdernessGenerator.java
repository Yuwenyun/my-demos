package com.yuwenyun.demos.flink.streaming;

import javax.annotation.Nullable;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-03 11:45
 */
public class BoundedOutOfOrdernessGenerator implements AssignerWithPeriodicWatermarks<String> {

    private long maxOutOfOrder = 10;
    private long currentMaxTimestamp;

    // 只有返回的Watermark非空且比当前的大时才emit
    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentMaxTimestamp - maxOutOfOrder);
    }

    @Override
    public long extractTimestamp(String element, long previousElementTimestamp) {
        // 从数据中提取timestamp
        String[] elements = element.split(" ");
        long timestamp = Long.parseLong(elements[0]);

        // 更新当前最新的timestamp
        currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);

        return timestamp;
    }
}
