package com.wenky.commons.dubbo.codec;

import static org.apache.dubbo.common.constants.CommonConstants.DUBBO_VERSION_KEY;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.DECODE_IN_IO_THREAD_KEY;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.DEFAULT_DECODE_IN_IO_THREAD;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.Version;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.io.UnsafeByteArrayInputStream;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.exchange.Request;
import org.apache.dubbo.remoting.exchange.Response;
import org.apache.dubbo.remoting.transport.CodecSupport;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.protocol.dubbo.DecodeableRpcInvocation;
import org.apache.dubbo.rpc.protocol.dubbo.DecodeableRpcResult;
import org.apache.dubbo.rpc.protocol.dubbo.DubboCodec;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-08 15:25
 */
@Slf4j
public class IDubboCodec extends DubboCodec {

    // DubboProtocol#createServer 336，URL初始化到参数中 codec:dubbo
    public static final String NAME = "iDubbo";

    @Override
    protected Object decodeBody(Channel channel, InputStream is, byte[] header) throws IOException {
        byte flag = header[2], proto = (byte) (flag & SERIALIZATION_MASK);
        // get request id.
        long id = Bytes.bytes2long(header, 4);
        if ((flag & FLAG_REQUEST) == 0) {
            // decode response.
            Response res = new Response(id);
            if ((flag & FLAG_EVENT) != 0) {
                res.setEvent(true);
            }
            // get status.
            byte status = header[3];
            res.setStatus(status);
            try {
                if (status == Response.OK) {
                    Object data;
                    if (res.isEvent()) {
                        ObjectInput in = CodecSupport.deserialize(channel.getUrl(), is, proto);
                        data = decodeEventData(channel, in);
                    } else {
                        DecodeableRpcResult result;
                        if (channel.getUrl()
                                .getParameter(
                                        DECODE_IN_IO_THREAD_KEY, DEFAULT_DECODE_IN_IO_THREAD)) {
                            result =
                                    new IDecodeableRpcResult(
                                            channel,
                                            res,
                                            is,
                                            (Invocation) getRequestData(id),
                                            proto);
                            result.decode();
                        } else {
                            result =
                                    new IDecodeableRpcResult(
                                            channel,
                                            res,
                                            new UnsafeByteArrayInputStream(readMessageData(is)),
                                            (Invocation) getRequestData(id),
                                            proto);
                        }
                        data = result;
                    }
                    res.setResult(data);
                } else {
                    ObjectInput in = CodecSupport.deserialize(channel.getUrl(), is, proto);
                    res.setErrorMessage(in.readUTF());
                }
            } catch (Throwable t) {
                if (log.isWarnEnabled()) {
                    log.warn("Decode response failed: " + t.getMessage(), t);
                }
                res.setStatus(Response.CLIENT_ERROR);
                res.setErrorMessage(StringUtils.toString(t));
            }
            return res;
        } else {
            // decode request.
            Request req = new Request(id);
            req.setVersion(Version.getProtocolVersion());
            req.setTwoWay((flag & FLAG_TWOWAY) != 0);
            if ((flag & FLAG_EVENT) != 0) {
                req.setEvent(true);
            }
            try {
                Object data;
                if (req.isEvent()) {
                    ObjectInput in = CodecSupport.deserialize(channel.getUrl(), is, proto);
                    data = decodeEventData(channel, in);
                } else {
                    DecodeableRpcInvocation inv;
                    if (channel.getUrl()
                            .getParameter(DECODE_IN_IO_THREAD_KEY, DEFAULT_DECODE_IN_IO_THREAD)) {
                        inv = new DecodeableRpcInvocation(channel, req, is, proto);
                        inv.decode();
                    } else {
                        inv =
                                new DecodeableRpcInvocation(
                                        channel,
                                        req,
                                        new UnsafeByteArrayInputStream(readMessageData(is)),
                                        proto);
                    }
                    data = inv;
                }
                req.setData(data);
            } catch (Throwable t) {
                if (log.isWarnEnabled()) {
                    log.warn("Decode request failed: " + t.getMessage(), t);
                }
                // bad request
                req.setBroken(true);
                req.setData(t);
            }

            return req;
        }
    }

    private byte[] readMessageData(InputStream is) throws IOException {
        if (is.available() > 0) {
            byte[] result = new byte[is.available()];
            is.read(result);
            return result;
        }
        return new byte[] {};
    }

    @Override
    protected void encodeResponseData(
            Channel channel, ObjectOutput out, Object data, String version) throws IOException {
        Result result = (Result) data;
        // currently, the version value in Response records the version of Request
        boolean attach = Version.isSupportResponseAttachment(version);

        Object ret = result.getValue();
        if (DubboInvokeResult.class.isInstance(ret)) {
            out.writeByte(attach ? RESPONSE_VALUE_WITH_ATTACHMENTS : RESPONSE_VALUE);
            out.writeObject(ret);
            if (attach) {
                // returns current version of Response to consumer side.
                result.getObjectAttachments().put(DUBBO_VERSION_KEY, Version.getProtocolVersion());
                out.writeAttachments(result.getObjectAttachments());
            }
            return;
        }

        Throwable th = result.getException();
        if (th == null) {
            if (ret == null) {
                out.writeByte(attach ? RESPONSE_NULL_VALUE_WITH_ATTACHMENTS : RESPONSE_NULL_VALUE);
            } else {
                out.writeByte(attach ? RESPONSE_VALUE_WITH_ATTACHMENTS : RESPONSE_VALUE);
                out.writeObject(ret);
            }
        } else {
            out.writeByte(
                    attach ? RESPONSE_WITH_EXCEPTION_WITH_ATTACHMENTS : RESPONSE_WITH_EXCEPTION);
            out.writeThrowable(th);
        }

        if (attach) {
            // returns current version of Response to consumer side.
            result.getObjectAttachments().put(DUBBO_VERSION_KEY, Version.getProtocolVersion());
            out.writeAttachments(result.getObjectAttachments());
        }
    }
}
