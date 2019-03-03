package org.kylin.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
public class LogTraceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("config={}", filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest) {
            String requestId = String.valueOf(System.currentTimeMillis() / 1000 + RandomStringUtils.randomNumeric(4));
            MDC.put("requestId", requestId);
            recordDetailLog(request);
        }

        chain.doFilter(request, response);
    }

    private void recordDetailLog(ServletRequest request) {
        log.info("request ip={},serverName={}", getIp((HttpServletRequest)request), request.getServerName());
    }

    public static String getIp(HttpServletRequest request) {

        try {
            String ip = request.getHeader("X-Real-IP");
            if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
            ip = request.getHeader("X-Forwarded-For");
            if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            } else {
                return request.getRemoteAddr();
            }
        } catch (Exception e) {
            return request.getRemoteAddr();
        }
    }

    @Override
    public void destroy() {
    }
}
