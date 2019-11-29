package com.yuwenyun.demos.flink.cep;

import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-11-29 15:42
 */
public class Patterns {

    /**
     * 定义广告主show_cnt大于10
     */
    public static Pattern getCountPattern(){
        return Pattern.<AdvertiserInfo>begin("start")
            .oneOrMore()
            .where(new SimpleCondition<AdvertiserInfo>() {
                @Override
                public boolean filter(AdvertiserInfo advertiser) throws Exception {
                    System.out.println("input adv: " + advertiser.getAdvertiserId());
                    return advertiser.getShowCnt() > 0;
                }
            })
            .next("middle")
            .oneOrMore()
            .where(new IterativeCondition<AdvertiserInfo>() {
                @Override
                public boolean filter(AdvertiserInfo advertiser, Context<AdvertiserInfo> context) throws Exception {
                    int sum = advertiser.getShowCnt();
                    for (AdvertiserInfo adv : context.getEventsForPattern("start")) {
                        if (adv.getAdvertiserId() != advertiser.getAdvertiserId()) {
                            continue;
                        }
                        sum += adv.getShowCnt();
                    }
                    return sum > 10;
                }
            });
    }
}
