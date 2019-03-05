package org.kylin.filters;

import lombok.extern.slf4j.Slf4j;
import org.kylin.constant.Constants;
import org.kylin.util.CommonUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class AuthFilter implements Filter {




    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) request;
        String servletPath = req.getServletPath();
        log.info("servletPath: "+servletPath);

        Object token = req.getSession().getAttribute(Constants.LOGIN_STATUS_KEY);
        if(Objects.equals(token, Constants.LOGIN_SUCCESS)){
            chain.doFilter(request, response);
            return;
        }

        // 判定是否是谷歌浏览器，临时放行
        if(CommonUtils.isGoogleBrowser(req)){
            log.info("谷歌浏览器临时放行");
            chain.doFilter(request, response);
            return;
        }


        log.warn("非登录访问 client:{},server:{},uri:{}", CommonUtils.getIp(req), req.getServerName(), req.getRequestURI());
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendRedirect("/login?origin=" + servletPath);
//        req.getRequestDispatcher("/login").forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
