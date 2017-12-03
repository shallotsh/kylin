package org.kylin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huangyawu
 * @date 2017/7/9 下午9:43.
 */
@Controller
public class Home {

    @RequestMapping("/")
    public String index(){
        return "wyf";
    }

    @RequestMapping("/origin")
    public String origin(){
        return "v1";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
