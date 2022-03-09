package com.workflow.general_backend.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfiguration {
    //订单队列
    public static final String ORDER_QUEUE = "order_queue";
    public static final String ORDER_EXCHANGE = "order_exchange";
    public static final String ORDER_ROUTE_KEY = "order_route_key";

    //死信队列
    public static final String DEAD_QUEUE = "dead_queue";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_ROUTE_KEY = "dead_route_key";


    //队列过期时间
    private int orderQueueTTL = 5000;

    // 配置普通队列
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE)
                .ttl(orderQueueTTL)
                .deadLetterRoutingKey(DEAD_ROUTE_KEY)//设置死信队列的RouteKey
                .deadLetterExchange(DEAD_EXCHANGE)//设置死信队列的Exchange
                .build();
    }

    @Bean
    public TopicExchange orderTopicExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue())
                .to(orderTopicExchange())
                .with(ORDER_ROUTE_KEY);
    }

    //配置死信队列
    @Bean
    public Queue deadQueue() {
        return new Queue(DEAD_QUEUE, true);
    }

    @Bean
    public TopicExchange deadExchange() {
        return new TopicExchange(DEAD_EXCHANGE);
    }

    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue())
                .to(deadExchange())
                .with(DEAD_ROUTE_KEY);

    }


}
