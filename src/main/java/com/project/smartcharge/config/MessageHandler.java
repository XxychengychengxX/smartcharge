/**
 * @author Valar Morghulis
 * @Date 2023/5/26
 */
package com.project.smartcharge.config;

import com.alibaba.fastjson.JSON;
import com.project.smartcharge.pojo.Bill;
import com.project.smartcharge.service.ComplexService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static com.project.smartcharge.config.RabbitmqConfig.CREAT_Bill_QUEUE;


public class MessageHandler {


    ComplexService complexService;

    /**
     * 该监听器监听新建订单消息队列
     */
    @RabbitListener(queues = CREAT_Bill_QUEUE)
    public void creatBillHandler(Message message) {
        //获取到消息队列
        byte[] body = message.getBody();
        //转成Bill
        String s = new String(body);
        Bill Bill = (Bill) JSON.parse(s);

        //拿到订单的充电模式
        Boolean chargeMod = Bill.getChargeMod();
        //查找该模式下工作的充电桩

    }
}
