package com.wenky.commons.dubbo.filter;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-02 11:25
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class ExceptionConvertFilter implements Filter, Filter.Listener {

    // ProtocolFilterWrapper#buildInvokerChain
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        if (!appResponse.hasException()) {
            return;
        }
        if (GenericService.class == invoker.getInterface()) {
            return;
        }
        try {
            Throwable exception = appResponse.getException();
            // 如果是异常但不是运行时异常，不做处理(受检异常)
            // directly throw if it's checked exception
            if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
                return;
            }

            Class<?> returnType;

            // 如果是受检时异常，不做处理
            // mock处理不了这种情况，可以包装成RpcException
            // directly throw if the exception appears in the signature
            try {
                Method method =
                        invoker.getInterface()
                                .getMethod(
                                        invocation.getMethodName(), invocation.getParameterTypes());
                Class<?>[] exceptionClasses = method.getExceptionTypes();
                for (Class<?> exceptionClass : exceptionClasses) {
                    if (exception.getClass().equals(exceptionClass)) {
                        return;
                    }
                }
                returnType = method.getReturnType();
            } catch (NoSuchMethodException e) {
                return;
            }

            // for the exception not found in method's signature, print ERROR message in server's
            // log.
            log.error(
                    "Got unchecked and undeclared exception which called by "
                            + RpcContext.getContext().getRemoteHost()
                            + ". service: "
                            + invoker.getInterface().getName()
                            + ", method: "
                            + invocation.getMethodName()
                            + ", exception: "
                            + exception.getClass().getName()
                            + ": "
                            + exception.getMessage(),
                    exception);

            // 判断是否存在异常逻辑重写 通过重写DecodeableRpcResult#hasException方法，实现自定义对象优雅处理异常
            // SentinelDubboConsumerFilter#83 记录异常调用结果
            // CacheFilter#107 非异常结果进行缓存
            // 对Cluster的影响
            // 如果方法返回类型为DubboInvokeResult，转化为指定结果
            if (DubboInvokeResult.class == returnType) {
                // 原逻辑：DubboCodec#encodeResponseData 192,当exception存在时，仅执行writeThrowable
                // 重写逻辑：IDubboCodec#encodeResponseData 156,存在异常时，服务端可以正常响应value信息至客户端
                appResponse.setException(exception);
                DubboInvokeResult result = DubboInvokeResult.exception(exception);
                result.wrapperRpcException();
                appResponse.setValue(result);
                return;
            }

            // directly throw if exception class and interface class are in the same jar file.
            String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
            String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
            if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                return;
            }
            // directly throw if it's JDK exception
            String className = exception.getClass().getName();
            if (className.startsWith("java.") || className.startsWith("javax.")) {
                return;
            }
            // directly throw if it's dubbo exception
            if (exception instanceof RpcException) {
                return;
            }

            // otherwise, wrap with RuntimeException and throw back to the client
            appResponse.setException(new RuntimeException(StringUtils.toString(exception)));
        } catch (Throwable e) {
            log.warn(
                    "Fail to ExceptionFilter when called by "
                            + RpcContext.getContext().getRemoteHost()
                            + ". service: "
                            + invoker.getInterface().getName()
                            + ", method: "
                            + invocation.getMethodName()
                            + ", exception: "
                            + e.getClass().getName()
                            + ": "
                            + e.getMessage(),
                    e);
        }
    }

    @Override
    public void onError(Throwable e, Invoker<?> invoker, Invocation invocation) {
        log.error(
                "Got unchecked and undeclared exception which called by "
                        + RpcContext.getContext().getRemoteHost()
                        + ". service: "
                        + invoker.getInterface().getName()
                        + ", method: "
                        + invocation.getMethodName()
                        + ", exception: "
                        + e.getClass().getName()
                        + ": "
                        + e.getMessage(),
                e);
    }
}
