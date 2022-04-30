package com.thoughtmechanix.zuulsvr.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*
 * 所有的zuul过滤器必须扩展ZuulFilter类，并覆盖4个方法
 */
@Component
public class TrackingFilter extends ZuulFilter{
    private static final int      FILTER_ORDER =  1;
    private static final boolean  SHOULD_FILTER=true;
    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    FilterUtils filterUtils;

    // 设置过滤器类型
    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    // 设置过滤器执行顺序
    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    // 设置过滤器是否执行
    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    // 每次服务通过过滤器时所执行的代码
    @Override
    public Object run() {

        if (isCorrelationIdPresent()) {
           logger.debug("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        }
        else{
            filterUtils.setCorrelationId(generateCorrelationId());
            logger.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }

        RequestContext ctx = RequestContext.getCurrentContext();
        logger.debug("Processing incoming request for {}.",  ctx.getRequest().getRequestURI());
        return null;
    }

    private boolean isCorrelationIdPresent(){
        if (filterUtils.getCorrelationId() !=null){
            return true;
        }

        return false;
    }

    // 生成CorrelationId
    private String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }
}