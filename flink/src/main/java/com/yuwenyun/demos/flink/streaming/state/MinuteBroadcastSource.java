package com.yuwenyun.demos.flink.streaming.state;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-03 21:34
 */
public class MinuteBroadcastSource extends RichParallelSourceFunction<String> {

    private volatile boolean enabled;
    private volatile int lastUpdateMinute = -1;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        enabled = true;
    }

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while (enabled) {
            LocalDateTime now = LocalDateTime.now();
            int minute = now.getMinute();
//            if (minute != lastUpdateMinute) {
//                lastUpdateMinute = minute;
                String separator = readConfigs();
                ctx.collect(separator);
//            }
            Thread.sleep(1000);
        }
    }

    public String readConfigs(){
        URL url = MinuteBroadcastSource.class.getResource("/broadcast_separator.txt");
        try {
            List<String> lines = FileUtils.readLines(new File(url.getPath()));
            if (lines.size() > 0) {
                return lines.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

    @Override
    public void cancel() {
        enabled = false;
    }
}
