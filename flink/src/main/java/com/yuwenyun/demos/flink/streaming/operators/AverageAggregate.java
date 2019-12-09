package com.yuwenyun.demos.flink.streaming.operators;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-09 18:15
 */
public class AverageAggregate implements AggregateFunction<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Integer> {

    @Override
    public Tuple2<Integer, Integer> createAccumulator() {
        return Tuple2.of(0, 0);
    }

    // 第二个参数是accumulator，返回新的accumulator
    @Override
    public Tuple2<Integer, Integer> add(Tuple2<Integer, Integer> inputTuple, Tuple2<Integer, Integer> acc) {
        return Tuple2.of(inputTuple.f0 + acc.f0, acc.f1 + 1);
    }

    @Override
    public Integer getResult(Tuple2<Integer, Integer> acc) {
        return acc.f0 / acc.f1;
    }

    @Override
    public Tuple2<Integer, Integer> merge(Tuple2<Integer, Integer> acc1, Tuple2<Integer, Integer> acc2) {
        return Tuple2.of(acc1.f0 + acc2.f0, acc1.f1 + acc2.f1);
    }
}
