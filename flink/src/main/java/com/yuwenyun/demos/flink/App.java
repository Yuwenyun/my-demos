package com.yuwenyun.demos.flink;

import com.yuwenyun.demos.flink.cep.CEPMain;
import com.yuwenyun.demos.flink.streaming.StreamMain;
import com.yuwenyun.demos.flink.streaming.state.StateMain;
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

    private final static String filePath = "/txt_input.txt";

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> source = getDataStreamFromSocket(env, "127.0.0.1", 9999);
//        CEPMain.attachProcessors(source);
//        StateMain.attachProcessors(source);
        StateMain.attachBroadcastProcessors(source, env);

        env.execute();
    }

    private static DataStream getDataStreamFromFile(StreamExecutionEnvironment env, String path){
        URL url = CEPMain.class.getResource(path);
        return env.readTextFile("file://" + url.getPath());
    }

    private static DataStream getDataStreamFromElements(StreamExecutionEnvironment env, List<String> list){
        return env.fromElements(list.toArray());
    }

    private static DataStream getDataStreamFromSocket(StreamExecutionEnvironment env, String ip, int port){
        return env.socketTextStream(ip, port);
    }
}
