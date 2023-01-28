package com.wenky.provider.dubbo;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-10 09:02
 */
public class ExtensionLoaderExample {
    public static void main(String[] args) {
        //
        Protocol protocol =
                ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("dubbo");

        System.out.println(protocol.getDefaultPort());
    }
}
