package com.yuwenyun.demos.flink.streaming;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-09 11:55
 */
public class StringToTupleMap implements MapFunction<String, Tuple2<Integer, Integer>> {

    @Override
    public Tuple2<Integer, Integer> map(String s) throws Exception {
        String[] strings = s.split(" ");
        return Tuple2.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
    }
}
