/**
 * @author Valar Morghulis
 * @Date 2023/5/26
 */
package com.project.smartcharge.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;


public class RabbitmqConfig {

    //新订单到来的消息队列
    protected static final String CREAT_Bill_QUEUE = "creatBillQueue";
    //充电桩完成充电的消息队列
    protected static final String FINISH_CHARGE_QUEUE = "finishChargeQueue";

    protected static final String CANCEL_Bill_QUEUE = "cancelBillQueue";

    protected static final String FANOUT_EXCHANGE = "fanoutExchange";

    @Bean(CREAT_Bill_QUEUE)
    public Queue newBillQueue() {
        return new Queue(CREAT_Bill_QUEUE, true, false, false, null);
    }

    @Bean(FINISH_CHARGE_QUEUE)
    public Queue compeleteChargeQueue() {
        return new Queue(FINISH_CHARGE_QUEUE, true, false, false, null);
    }

    @Bean(CANCEL_Bill_QUEUE)
    public Queue cancelBillQueue() {
        return new Queue(CANCEL_Bill_QUEUE, true, false, false, null);
    }


    @Bean(FANOUT_EXCHANGE)
    public Exchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding FanoutQueueBinding1(@Qualifier(CREAT_Bill_QUEUE) Queue queue1,
                                       @Qualifier(FANOUT_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with("").noargs();
    }

    @Bean
    public Binding FanoutQueueBinding2(@Qualifier(FINISH_CHARGE_QUEUE) Queue queue1,
                                       @Qualifier(FANOUT_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with("").noargs();
    }

    @Bean
    public Binding FanoutQueueBinding3(@Qualifier(CANCEL_Bill_QUEUE) Queue queue1,
                                       @Qualifier(FANOUT_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with("").noargs();
    }


}
