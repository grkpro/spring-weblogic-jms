package com.rk.weblogic.jms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * @author ravikumar.gowri
 * @Date 8/15/2022
 */
@Configuration
@EnableJms
public class JmsConfiguration {

    @Value(value = "${weblogic.hostAddress}")
    private String hostAddress;

    @Value(value = "${weblogic.connection.factory}")
    private String connectionFactoryJndi;

    private Properties getJndiProperties() {
        final Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        properties.setProperty(Context.PROVIDER_URL, hostAddress);
        return properties;
    }

    @Bean
    public JndiTemplate jndiTemplate() {
        JndiTemplate jndiTemplate = new JndiTemplate();
        jndiTemplate.setEnvironment(getJndiProperties());
        return jndiTemplate;
    }

    public JndiObjectFactoryBean jndiConnectionFactory() throws NamingException {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName(connectionFactoryJndi);
        jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
        jndiObjectFactoryBean.afterPropertiesSet();
        return jndiObjectFactoryBean;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsContainer() throws NamingException {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        JndiDestinationResolver jndiDestinationResolver = new JndiDestinationResolver();
        jndiDestinationResolver.setJndiTemplate(jndiTemplate());
        defaultJmsListenerContainerFactory.setDestinationResolver(jndiDestinationResolver);
        defaultJmsListenerContainerFactory.setConnectionFactory((ConnectionFactory) jndiConnectionFactory().getObject());
        return defaultJmsListenerContainerFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws NamingException {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory((ConnectionFactory) jndiConnectionFactory().getObject());
        JndiDestinationResolver jndiDestinationResolver = new JndiDestinationResolver();
        jndiDestinationResolver.setJndiTemplate(jndiTemplate());
        jndiDestinationResolver.setCache(true);
        jmsTemplate.setDestinationResolver(jndiDestinationResolver);
        return jmsTemplate;
    }
}
