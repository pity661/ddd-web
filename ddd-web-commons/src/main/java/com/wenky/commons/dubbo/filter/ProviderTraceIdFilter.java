package com.wenky.commons.dubbo.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-09 17:13
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class ProviderTraceIdFilter extends AbstractTraceIdFilter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        getTranceIdFromAttachment();
        return invoker.invoke(invocation);
    }
}
