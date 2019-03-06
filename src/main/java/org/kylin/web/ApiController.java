package org.kylin.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {
    @RequestMapping(value = "/monitor/alive", method = RequestMethod.GET)
    public Map<String, String> check(){
        Map<String, String> status = new HashMap<>();
        status.put("status", "ok");
        return status;
    }


    @RequestMapping(value = "/async/monitor/alive", method = RequestMethod.GET)
    public Callable<Map<String, String>> checkAsync(){

        log.info("start monitor.");

        Callable<Map<String, String>> status = () -> {

            Thread.sleep(100);


            Map<String, String> st = new HashMap<>();
            st.put("status", "ok");

            log.debug("complete check status.");

            return st;

        };

        log.debug("return monitor.");

        return status;
    }
}
