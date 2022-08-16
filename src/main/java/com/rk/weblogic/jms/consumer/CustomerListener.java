package com.rk.weblogic.jms.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author ravikumar.gowri
 * @Date 8/15/2022
 */
@Component
public class CustomerListener {
    @JmsListener(destination = "${weblogic.queue}",containerFactory = "jmsContainer")
    public void onMessage(String message){
        System.out.println(message);
    }
}
