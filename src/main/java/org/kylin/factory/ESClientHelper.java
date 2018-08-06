package org.kylin.factory;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ESClientHelper {

    private Settings settings;

    private Map<String, Client> clusterToClientMap = new ConcurrentHashMap<>();

    private Map<String, Integer> hostToPort = new HashMap<>();

    private String clusterName = "Home";


    public ESClientHelper() {
        init();
    }

    public static final ESClientHelper getInstance(){
        return ClientHolder.CLIENT_INSTANCE;
    }

    private static class ClientHolder{
        private static final ESClientHelper CLIENT_INSTANCE =  new ESClientHelper();
    }


    private void init(){
        try {
            hostToPort.put("127.0.0.1", 9300);
            settings = Settings.builder()
                    .put("client.transport.sniff", true)
                    .put("cluster.name", clusterName)
                    .build();
            addClient(settings, getAllTransportAddresses(hostToPort));
        } catch (Exception e) {
            log.info("连接ES失败", e);
        }
    }

    public List<TransportAddress> getAllTransportAddresses(Map<String, Integer> hostToPort){
        List<TransportAddress> transportAddresses = new ArrayList<>();
        for(String host : hostToPort.keySet()){
            try {
                transportAddresses.add(new TransportAddress(InetAddress.getByName(host), hostToPort.get(host)));
            } catch (UnknownHostException e) {
                log.info("解析es主机异常", e);
            }
        }
        return transportAddresses;
    }


    public void addClient(Settings settings, List<TransportAddress> transportAddresses){
        Client client = new PreBuiltTransportClient(settings)
                .addTransportAddresses(transportAddresses.toArray(new TransportAddress[transportAddresses.size()]));
        clusterToClientMap.put(settings.get("cluster.name"), client);
    }


    public Client getClient(){
        return clusterToClientMap.get(clusterName);
    }

    public Client getClient(String clusterName){
        return clusterToClientMap.get(clusterName);
    }

}
