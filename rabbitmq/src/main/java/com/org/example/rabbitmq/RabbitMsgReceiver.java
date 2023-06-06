package com.org.example.rabbitmq;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.org.example.dao.RaffleRecordMapper;
import com.org.example.entity.RaffleVO;
import com.org.example.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Rabbit msg receiver.
 */
@Component
@Slf4j
public class RabbitMsgReceiver {

    @Resource
    RaffleRecordMapper raffleRecordMapper;

    /**
     * Receive msg.
     *
     * @param msg the msg
     */
    @RabbitListener(queues = "draw")
    public void receiveMsg(String msg) throws JsonProcessingException {
        if (StringUtils.hasText(msg)) {
            List<RaffleVO> raffleVO = JsonUtils.fromJsonList(msg, RaffleVO.class);
            int insert = raffleRecordMapper.insertList(raffleVO);
            if (insert > 0) {
                log.info("插入成功{}", msg);
            } else {
                log.error("插入失败{}", msg);
            }
        }
    }

}
