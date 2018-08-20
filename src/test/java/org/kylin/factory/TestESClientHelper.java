package org.kylin.factory;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class TestESClientHelper extends ESClientHelper {

//    private static final Logger LOGGER = LoggerFactory.getLogger(TestESClientHelper.class);
//
//    @Test
//    public void testGet() throws Exception{
//
//        Settings settings = Settings.builder()
//                .put("cluster.name", "Home").build();
//
//        TransportClient client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//
//        GetResponse response = client.prepareGet("people", "man", "23298" ).get();
////        Assert.isNull(response, "返回值为空");
//
//        LOGGER.info("resp_source: {}, obj:{}", response.getSource(), JSON.toJSONString(response));
//    }
//
//    @Test
//    public void testESClientHelper(){
//        GetResponse response = ESClientHelper.getInstance().getClient().prepareGet("people", "man", "23298" ).get();
//        LOGGER.info("resp_source2: {}, obj:{}", response.getSource(), JSON.toJSONString(response));
//    }
}
