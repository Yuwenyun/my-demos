package com.yuwenyun.demos.flink.streaming.state;

import com.yuwenyun.demos.flink.streaming.StringToTupleMap;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-03 15:54
 */
public class StateMain {

    private static final MapStateDescriptor<String, String> CONFIG_DESCRIPTOR = new MapStateDescriptor<String, String>(
        "separator_config",
        BasicTypeInfo.STRING_TYPE_INFO,
        BasicTypeInfo.STRING_TYPE_INFO
    );

    public static void attachProcessors(DataStream<String> source){
        source.map(new StringToTupleMap()).keyBy(0).flatMap(new CountAverage()).addSink(new BufferedSink(2));
    }

//    public static void attachQueryableProcessors(DataStream<String> source){
//        source.map(new MapFunction<String, Tuple2<Integer, Integer>>() {
//            @Override
//            public Tuple2<Integer, Integer> map(String s) throws Exception {
//                String[] strings = s.split(" ");
//                return Tuple2.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
//            }
//        }).keyBy(0).asQueryableState("query-name");
//    }

    public static void attachBroadcastProcessors(DataStream<String> source, StreamExecutionEnvironment env){
        // 构建广播流
        BroadcastStream<String> broadcastStream = env.addSource(new MinuteBroadcastSource())
            .setParallelism(1)
            .broadcast(CONFIG_DESCRIPTOR);
        // 主流程连接广播流
        source.connect(broadcastStream).process(new BroadcastProcessFunction<String, String, Tuple2<Integer, Integer>>() {

            @Override
            public void processElement(String value, ReadOnlyContext readOnlyContext,
                Collector<Tuple2<Integer, Integer>> collector) throws Exception {
                // 获取广播流中的状态数据
                String separator = readOnlyContext.getBroadcastState(CONFIG_DESCRIPTOR).get("separator");
                String[] values = value.split(separator);
                collector.collect(Tuple2.of(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
            }

            @Override
            public void processBroadcastElement(String broadcastValue, Context context,
                Collector<Tuple2<Integer, Integer>> collector) throws Exception {
                // 处理广播流上的事件，对mapState可读可写
                context.getBroadcastState(CONFIG_DESCRIPTOR).put("separator", broadcastValue);
            }
        }).print();
    }
}
