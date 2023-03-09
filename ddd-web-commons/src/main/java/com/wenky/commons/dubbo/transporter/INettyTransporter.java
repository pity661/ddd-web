package com.wenky.commons.dubbo.transporter;

import com.wenky.commons.dubbo.codec.IDubboCodec;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.*;
import org.apache.dubbo.remoting.transport.netty4.NettyTransporter;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-08 18:13
 */
public class INettyTransporter extends NettyTransporter {

    // DubboProtocol#createServer 338，URL初始化到参数中 server:netty
    // AbstractEndpoint#getChannelCodec 53 加载Codec2的地方
    public static final String NAME = "iNetty";

    @Override
    public RemotingServer bind(URL url, ChannelHandler handler) throws RemotingException {
        url = url.addParameter(Constants.CODEC_KEY, IDubboCodec.NAME);
        return super.bind(url, handler);
    }

    @Override
    public Client connect(URL url, ChannelHandler handler) throws RemotingException {
        url = url.addParameter(Constants.CODEC_KEY, IDubboCodec.NAME);
        return super.connect(url, handler);
    }
}
