package com.yuwenyun.demos.flink.streaming.state;

import java.util.LinkedList;
import java.util.List;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

/**
 * Operator State, 通过CheckpointedFunction提供访问none-keyed State的方法
 * @author lijing
 * @version 1.0
 * @date 2019-12-03 18:36
 */
public class BufferedSink extends PrintSinkFunction<Tuple2<Integer, Integer>> implements CheckpointedFunction {

    private final int threshold;
    private List<Tuple2<Integer, Integer>> bufferedElements;

    // 每个算子的checkpointState里保存的数据的list，在状态重新分配时（比如发生rebalance）根据以下策略重新分配
    // Even-split redistribution: 所有本类的实例保存的checkpointState中的数据拼接在一起重新分配
    // Union redistribution: 所有本类的checkpointState中数据拼接在一起，状态重新分配时所有实例获得全量状态
    private transient ListState<Tuple2<Integer, Integer>> checkpointState;

    public BufferedSink(int threshold){
        this.threshold = threshold;
        bufferedElements = new LinkedList<>();
    }

    @Override
    public void invoke(Tuple2<Integer, Integer> value, Context context) throws Exception {
        bufferedElements.add(value);
        if (bufferedElements.size() == threshold) {
            for (Tuple2<Integer, Integer> tuple : bufferedElements) {
                super.invoke(tuple);
            }
            bufferedElements.clear();
        }
    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {
        checkpointState.clear();
        for (Tuple2<Integer, Integer> tuple : bufferedElements) {
            checkpointState.add(tuple);
        }
    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        ListStateDescriptor<Tuple2<Integer, Integer>> descriptor = new ListStateDescriptor<Tuple2<Integer, Integer>>(
            "buffered_elements",
            TypeInformation.of(new TypeHint<Tuple2<Integer, Integer>>() {})
        );
        // 使用Even-split redistribution策略
        checkpointState = context.getOperatorStateStore().getListState(descriptor);
        // 使用Union redistribution策略
//        checkpointState = context.getOperatorStateStore().getUnionListState(descriptor);
        if (context.isRestored()) {
            for (Tuple2<Integer, Integer> tuple : checkpointState.get()) {
                bufferedElements.add(tuple);
            }
        }
    }
}
