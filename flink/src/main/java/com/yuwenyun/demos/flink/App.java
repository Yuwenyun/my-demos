package com.yuwenyun.demos.flink;

import java.net.URL;
import java.util.List;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-11-29 14:52
 */
public class App {

    public static void main(String[] args) throws Exception {
    }

    private static DataStream getDataStreamFromFile(StreamExecutionEnvironment env, String path){
        URL url = App.class.getResource(path);
        return env.readTextFile("file://" + url.getPath());
    }

    private static DataStream getDataStreamFromElements(StreamExecutionEnvironment env, List<String> list){
        return env.fromElements(list.toArray());
    }

    private static DataStream getDataStreamFromSocket(StreamExecutionEnvironment env, String ip, int port){
        return env.socketTextStream(ip, port);
    }
}
