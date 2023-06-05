package com.org.example.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * The type Rabbit msg receiver.
 */
@Component
@Slf4j
public class RabbitMsgReceiver {


    /**
     * Receive msg.
     *
     * @param msg the msg
     */
    @RabbitListener(queues = "draw")
    public void receiveMsg(String msg) {
        System.out.println("接收到的消息：" + msg);
    }


}
