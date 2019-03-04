package org.kylin.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.util.CommonUtils;
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
        log.info("request ip={},serverName={}, uri={}, authType={}", CommonUtils.getIp((HttpServletRequest)request),
                request.getServerName(), ((HttpServletRequest) request).getRequestURI(), ((HttpServletRequest) request).getAuthType());
    }



    @Override
    public void destroy() {
    }
}
