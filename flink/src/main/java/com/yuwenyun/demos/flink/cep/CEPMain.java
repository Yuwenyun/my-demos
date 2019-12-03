package com.yuwenyun.demos.flink.cep;

import java.net.URL;
import java.util.List;
import java.util.Map;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-11-29 14:52
 */
public class CEPMain {

    private static final ObjectMapper mapper = new ObjectMapper();

    // source : {"advertiserId": 1, "showCnt": 2}
    public static void attachProcessors(DataStream<String> source){
        DataStream<AdvertiserInfo> mappedStream = source.map(
            new MapFunction<String, AdvertiserInfo>() {
                @Override
                public AdvertiserInfo map(String value) throws Exception {
                    return mapper.readValue(value, AdvertiserInfo.class);
                }
            });
        PatternStream<AdvertiserInfo> patternedStream = CEP.pattern(mappedStream, Patterns.getCountPattern());
        patternedStream.process(new PatternProcessFunction<AdvertiserInfo, String>() {
            @Override
            public void processMatch(Map<String, List<AdvertiserInfo>> map, Context context,
                Collector<String> collector) throws Exception {
                List<AdvertiserInfo> advertiserInfos = map.get("middle");
                for (AdvertiserInfo adv : advertiserInfos) {
                    collector.collect(adv.getAdvertiserId() + "");
                }
            }
        }).print();
    }
}
