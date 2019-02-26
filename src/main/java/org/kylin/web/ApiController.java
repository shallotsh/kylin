package org.kylin.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    @RequestMapping(value = "/monitor/alive", method = RequestMethod.GET)
    public Map<String, String> check(){
        Map<String, String> status = new HashMap<>();
        status.put("status", "ok");
        return status;
    }
}
