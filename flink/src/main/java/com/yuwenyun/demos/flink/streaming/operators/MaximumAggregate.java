package com.yuwenyun.demos.flink.streaming.operators;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-09 18:25
 */
public class MaximumAggregate implements AggregateFunction<Tuple2<Integer, Integer>, Integer, Integer> {

    @Override
    public Integer createAccumulator() {
        return 0;
    }

    @Override
    public Integer add(Tuple2<Integer, Integer> inputValue, Integer acc) {
        return inputValue.f0 > acc ? inputValue.f0 : acc;
    }

    @Override
    public Integer getResult(Integer integer) {
        return integer;
    }

    @Override
    public Integer merge(Integer acc1, Integer acc2) {
        return acc1 + acc2;
    }
}
