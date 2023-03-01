package com.wenky.provider.dubbo;

import com.wenky.commons.dubbo.spi.serialization.jackson.JacksonSerialization;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.serialize.Serialization;
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
        Protocol protocol1 =
                ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("injvm");

        JacksonSerialization jacksonSerialization = new JacksonSerialization();

        Serialization serialization =
                ExtensionLoader.getExtensionLoader(Serialization.class).getExtension("jackson");

        System.out.println(protocol.getDefaultPort());
        System.out.println(protocol1.getDefaultPort());
        System.out.println(serialization.getContentType());
    }
}
