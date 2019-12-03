package com.yuwenyun.demos.flink.streaming.state;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.StateTtlConfig.StateVisibility;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * Keyed State, 此类是有状态的，定义的valueState和key相关，和使用此类的算子相关
 * @author lijing
 * @version 1.0
 * @date 2019-12-03 15:43
 */
public class CountAverage extends RichFlatMapFunction<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> {

    private transient ValueState<Tuple2<Integer, Integer>> valueState;

    @Override
    public void flatMap(Tuple2<Integer, Integer> tuple, Collector<Tuple2<Integer, Integer>> collector)
        throws Exception {
        Tuple2<Integer, Integer> currentSum = valueState.value();
        // 使用了ttl配置这里拿不到结果，后面update也无效
        if (currentSum == null) {
            currentSum = Tuple2.of(0, 0);
        }
        currentSum.f0 += 1;
        currentSum.f1 += tuple.f1;
        // 更新到状态中
        valueState.update(currentSum);

        if (currentSum.f0 >= tuple.f0 + 1) {
            collector.collect(Tuple2.of(tuple.f0, currentSum.f1 / currentSum.f0));
            valueState.clear();
        }
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        ValueStateDescriptor<Tuple2<Integer, Integer>> descriptor = new ValueStateDescriptor<Tuple2<Integer, Integer>>(
            "average",  // state命名
            TypeInformation.of(new TypeHint<Tuple2<Integer, Integer>>() {}),  // state存储数据类型
            Tuple2.of(0, 0)  // state默认值
        );

        StateTtlConfig ttlConfig = StateTtlConfig
            .newBuilder(Time.seconds(3))
            .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite) // 仅在创建和写入时更新
            .setStateVisibility(StateVisibility.NeverReturnExpired) // 过期未清理的情况下是否可见
            .build();
        // 使用ttl配置valueState的默认值设置不生效
//        descriptor.enableTimeToLive(ttlConfig);

        // Keyed State与当前分区的key和当前算子相关，从RuntimeContext获取
        valueState = getRuntimeContext().getState(descriptor);

    }
}
