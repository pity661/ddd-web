package com.wenky.commons.dubbo.codec;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import java.io.InputStream;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.exchange.Response;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.protocol.dubbo.DecodeableRpcResult;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-08 15:28
 */
public class IDecodeableRpcResult extends DecodeableRpcResult {

    public IDecodeableRpcResult(
            Channel channel, Response response, InputStream is, Invocation invocation, byte id) {
        super(channel, response, is, invocation, id);
    }

    // 针对MockClusterInvoker#invoke 101，需要将异常包装成RpcException，并且重写getException方法
    @Override
    public Throwable getException() {
        Object result = getValue();
        if (DubboInvokeResult.class.isInstance(result)) {
            return ((DubboInvokeResult) result).getException();
        }
        return super.getException();
    }

    @Override
    public boolean hasException() {
        Object result = getValue();
        if (DubboInvokeResult.class.isInstance(result)) {
            return ((DubboInvokeResult) result).getException() != null;
        }
        return super.hasException();
    }
}
