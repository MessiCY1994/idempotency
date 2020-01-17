package com.messiyang.idempotency.mq;

import com.messiyang.idempotency.common.Constant;
import com.messiyang.idempotency.pojo.MsgLog;
import com.messiyang.idempotency.service.MsgLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.lang.reflect.Proxy;
import java.util.Map;

@Slf4j
public class BaseConsumerProxy {

    private Object target;

    private MsgLogService msgLogService;

    public BaseConsumerProxy(Object target, MsgLogService msgLogService) {
        this.target = target;
        this.msgLogService = msgLogService;
    }

    /**
     * 通过代理类来处理消息
     * @return
     */
    public Object getProxy() {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class[] interfaces = target.getClass().getInterfaces();

        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, (proxy1, method, args) -> {
            System.out.println(method.getName());
            if(!"consume".equals(method.getName())){
                return method.invoke(target,args);
            }
            Message message = (Message) args[0];
            Channel channel = (Channel) args[1];

            String correlationId = getCorrelationId(message);
            // 消费幂等性, 防止消息被重复消费
            if (isConsumed(correlationId)) {
                log.info("重复消费, correlationId: {}", correlationId);
                return null;
            }

            MessageProperties properties = message.getMessageProperties();
            long tag = properties.getDeliveryTag();

            try {
                // 真正消费的业务逻辑
                Object result = method.invoke(target, args);
                System.out.println("进行rabbitmq的ack操作之前");
                msgLogService.updateStatus(correlationId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
                // 代表消费者确认收到当前消息，第二个参数表示一次是否 ack 多条消息
                System.out.println("tsg:"+tag);
                channel.basicAck(tag, false);
                System.out.println("进行rabbitmq的ack操作");
                return result;
            } catch (Exception e) {
                log.error("getProxy error", e);
                // 代表消费者拒绝一条或者多条消息，第二个参数表示一次是否拒绝多条消息，第三个参数表示是否把当前消息重新入队
                channel.basicNack(tag, false, true);
                return null;
            }
        });

        return proxy;
    }

    /**
     * 获取CorrelationId
     *
     * @param message
     * @return
     */
    private String getCorrelationId(Message message) {
        String correlationId = null;

        MessageProperties properties = message.getMessageProperties();
        Map<String, Object> headers = properties.getHeaders();
        for (Map.Entry entry : headers.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.equals("spring_returned_message_correlation")) {
                correlationId = value;
            }
        }

        return correlationId;
    }

    /**
     * 消息是否已被消费
     *
     * @param correlationId
     * @return
     */
    private boolean isConsumed(String correlationId) {
        MsgLog msgLog = msgLogService.selectByMsgId(correlationId);
        if (null == msgLog || msgLog.getStatus().equals(Constant.MsgLogStatus.CONSUMED_SUCCESS)) {
            return true;
        }

        return false;
    }

}
