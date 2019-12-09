package com.yuwenyun.demos.flink.streaming.operators;

import com.yuwenyun.demos.flink.streaming.BoundedOutOfOrdernessGenerator;
import com.yuwenyun.demos.flink.streaming.StringToTupleMap;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-09 11:23
 */
public class WindowMain {

    // TumblingWindow是有固定时长大小，没有重叠的窗口
    public static void attachTumblingWindow(DataStream<String> source){
        source.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessGenerator())
            .map(new StringToTupleMap())
            .keyBy(1)
            // 当watermark比窗口endTime大时触发窗口后面的function
            .window(TumblingEventTimeWindows.of(Time.milliseconds(5)))
            .reduce((a, b) -> Tuple2.of(a.f0 + b.f0, a.f1))
            .print();
    }

    // SlidingWindow有固定的时长大小做为窗口大小，有滑动的时间步长，有可以游重叠的窗口
    public static void attachSlidingWindow(DataStream<String> source) {
        source.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessGenerator())
            .map(new StringToTupleMap())
            .keyBy(1)
            // 当watermark为时间步长整数倍时触发该watermark所在窗口的所有元素执行window function
            .window(SlidingEventTimeWindows.of(Time.milliseconds(10), Time.milliseconds(5)))
            .reduce((a, b) -> Tuple2.of(a.f0 + b.f0, a.f1))
            .print();
    }
}
