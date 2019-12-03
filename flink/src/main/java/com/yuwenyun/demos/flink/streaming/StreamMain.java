package com.yuwenyun.demos.flink.streaming;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-03 11:17
 */
public class StreamMain {

    // source :
    public static void attachProcessors(DataStream<String> source, StreamExecutionEnvironment env){

        // 设置event_time
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


    }
}
