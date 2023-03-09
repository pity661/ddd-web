package com.wenky.commons.dubbo.filter;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.MDC;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-09 17:18
 */
public abstract class AbstractTraceIdFilter implements Filter {

    private final String TRACE_ID = "traceId";
    private final String CLIENT_IP = "clientIp";

    protected void putTranceIdToAttachment() {
        String traceId = MDC.get(TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = UUID.randomUUID().toString().replaceAll("-", "");
            MDC.put(TRACE_ID, traceId);
        }
        RpcContext.getContext()
                .setAttachment(TRACE_ID, traceId)
                .setAttachment(CLIENT_IP, MDC.get(CLIENT_IP));
    }

    protected void getTranceIdFromAttachment() {
        MDC.put(TRACE_ID, RpcContext.getContext().getAttachment(TRACE_ID));
        MDC.put(CLIENT_IP, RpcContext.getContext().getAttachment(CLIENT_IP));
    }

    protected void cleanAttachment() {
        RpcContext.getContext().remove(TRACE_ID);
        RpcContext.getContext().remove(CLIENT_IP);
    }
}
