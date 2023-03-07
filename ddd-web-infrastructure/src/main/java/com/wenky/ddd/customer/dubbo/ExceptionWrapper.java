package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.provider.dubbo.service.IHelloService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcException;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 14:05
 */
@Slf4j
// @Component
public class ExceptionWrapper extends AbstractIHelloService {

    // 异常处理 ExceptionFilter 默认在provider中使用
    @DubboReference(version = "1.0", group = "custom1")
    private IHelloService iHelloService;

    @Override
    public DubboInvokeResult IOError() throws IOException {
        try {
            return iHelloService.IOError();
        } catch (RpcException | IOException e) {
            log.error("invoke error", e);
        }
        return null;
    }

    @Override
    public DubboInvokeResult RuntimeError() throws IOException {
        try {
            return iHelloService.RuntimeError();
        } catch (RpcException | IOException e) {
            log.error("invoke error", e);
        }
        return null;
    }
}
