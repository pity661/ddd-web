package com.wenky.commons.dubbo.codec;

import static org.apache.dubbo.rpc.Constants.INPUT_KEY;
import static org.apache.dubbo.rpc.Constants.OUTPUT_KEY;

import java.io.IOException;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.Codec2;
import org.apache.dubbo.remoting.buffer.ChannelBuffer;
import org.apache.dubbo.remoting.exchange.Request;
import org.apache.dubbo.remoting.exchange.Response;
import org.apache.dubbo.remoting.exchange.support.MultiMessage;
import org.apache.dubbo.rpc.AppResponse;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.rpc.protocol.dubbo.DubboCodec;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-08 16:23
 */
public class IDubboCountCodec implements Codec2 {

    private DubboCodec codec = new IDubboCodec();

    @Override
    public void encode(Channel channel, ChannelBuffer buffer, Object msg) throws IOException {
        codec.encode(channel, buffer, msg);
    }

    @Override
    public Object decode(Channel channel, ChannelBuffer buffer) throws IOException {
        int save = buffer.readerIndex();
        MultiMessage result = MultiMessage.create();
        do {
            Object obj = codec.decode(channel, buffer);
            if (Codec2.DecodeResult.NEED_MORE_INPUT == obj) {
                buffer.readerIndex(save);
                break;
            } else {
                result.addMessage(obj);
                logMessageLength(obj, buffer.readerIndex() - save);
                save = buffer.readerIndex();
            }
        } while (true);
        if (result.isEmpty()) {
            return Codec2.DecodeResult.NEED_MORE_INPUT;
        }
        if (result.size() == 1) {
            return result.get(0);
        }
        return result;
    }

    private void logMessageLength(Object result, int bytes) {
        if (bytes <= 0) {
            return;
        }
        if (result instanceof Request) {
            try {
                ((RpcInvocation) ((Request) result).getData())
                        .setAttachment(INPUT_KEY, String.valueOf(bytes));
            } catch (Throwable e) {
                /* ignore */
            }
        } else if (result instanceof Response) {
            try {
                ((AppResponse) ((Response) result).getResult())
                        .setAttachment(OUTPUT_KEY, String.valueOf(bytes));
            } catch (Throwable e) {
                /* ignore */
            }
        }
    }
}
