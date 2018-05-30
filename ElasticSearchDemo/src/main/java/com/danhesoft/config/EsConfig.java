package com.danhesoft.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Created by caowei on 2018/5/29.
 */
@Configuration
public class EsConfig {

    @Bean
    public TransportClient client()throws Exception{
        TransportAddress node = new TransportAddress(InetAddress.getByName("139.219.14.85"), 9300);

        Settings settings = Settings.builder().put("cluster.name", "caowei-es").build();

        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);
        return client;
    }

}
