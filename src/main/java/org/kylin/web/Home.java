package org.kylin.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kylin.bean.User;
import org.kylin.constant.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author huangyawu
 * @date 2017/7/9 下午9:43.
 */
@Controller
@Slf4j
public class Home {

    @RequestMapping("/")
    public String index(){
        return "wyf";
    }

    @RequestMapping("/origin")
    public String origin(){
        return "v1";
    }

    @RequestMapping("/2d")
    public String wyf2d(){
        return "wyf_2d";
    }


    @RequestMapping("/login")
    public String login(String origin, HttpServletRequest request){

        HttpSession session = request.getSession();
        session.removeAttribute(Constants.LOGIN_STATUS_KEY);
        String prePage;
        if(StringUtils.isNotEmpty(origin) && origin.startsWith("/")){
            prePage = origin;
        }else {
            prePage = request.getHeader("Referer");
        }
        session.setAttribute("prePage", prePage);
        log.info("prePage={}", prePage);

        return "login";
    }

    @RequestMapping(value = "/login/auth", method = RequestMethod.POST)
    public String loginCheck(User user, HttpServletRequest request){

        if(Objects.isNull(user)){
            return "redirect:/login";
        }

        log.info("login user:{}", user);
        HttpSession session = request.getSession();

        if("wyf".equals(user.getUserName())){

            // 登录成功
            session.setAttribute(Constants.LOGIN_STATUS_KEY, Constants.LOGIN_SUCCESS);
            session.setMaxInactiveInterval(7 * 24 * 60 * 60);

            Object prePage = session.getAttribute("prePage");
            if(Objects.isNull(prePage) || prePage.toString().endsWith("login")) {
                return "redirect:/";
            }else{
                return "redirect:" + prePage.toString();
            }
        }

        session.removeAttribute(Constants.LOGIN_STATUS_KEY);


        return "redirect:/login";
    }

}
