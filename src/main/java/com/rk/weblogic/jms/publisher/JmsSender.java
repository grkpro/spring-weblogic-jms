package com.rk.weblogic.jms.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author ravikumar.gowri
 * @Date 8/15/2022
 */
@Component
public class JmsSender {
    @Autowired
    public JmsTemplate jmsTemplate;

    @Value(value = "${weblogic.queue}")
    public String queue;

    public void send(String message) {
        jmsTemplate.send(queue, session -> session.createTextMessage(message));
    }
}
