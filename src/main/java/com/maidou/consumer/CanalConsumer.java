package com.maidou.consumer;

import com.alibaba.fastjson.JSONObject;
import com.maidou.model.CanalBean;
import com.maidou.model.TbCommodityInfo;
import com.maidou.util.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class CanalConsumer {

    @Resource
    private RedisClient redisClient;

    @KafkaListener(topics = "canaltopic")
    public void receive(ConsumerRecord<?, ?> consumer) {
        String value = (String) consumer.value();
        log.info("topic名称:{},key:{},分区位置:{},下标:{},value:{}", consumer.topic(), consumer.key(),
                consumer.partition(), consumer.offset(), value);
        //转换为javaBean
        CanalBean canalBean = JSONObject.parseObject(value, CanalBean.class);
        //获取是否是DDL语句
        boolean isDdl = canalBean.getIsDdl();
        //获取类型
        String type = canalBean.getType();
        //不是DDL语句
        if (!isDdl) {
            List<TbCommodityInfo> tbCommodityInfos = canalBean.getData();
            //过期时间
            long TIME_OUT = 600L;
            if ("INSERT".equals(type)) {
                //新增语句
                for (TbCommodityInfo tbCommodityInfo : tbCommodityInfos) {
                    String id = tbCommodityInfo.getId();
                    //新增到redis中,过期时间是10分钟
                    redisClient.setString(id, JSONObject.toJSONString(tbCommodityInfo), TIME_OUT);
                }
            } else if ("UPDATE".equals(type)) {
                //更新语句
                for (TbCommodityInfo tbCommodityInfo : tbCommodityInfos) {
                    String id = tbCommodityInfo.getId();
                    //更新到redis中,过期时间是10分钟
                    redisClient.setString(id, JSONObject.toJSONString(tbCommodityInfo), TIME_OUT);
                }
            } else {
                //删除语句
                for (TbCommodityInfo tbCommodityInfo : tbCommodityInfos) {
                    String id = tbCommodityInfo.getId();
                    //从redis中删除
                    redisClient.deleteKey(id);
                }
            }
        }
    }
}