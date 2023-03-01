package com.wenky.commons.dubbo.spi.serialization.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.apache.dubbo.common.serialize.ObjectOutput;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-02-06 18:36
 */
public class JacksonObjectOutput implements ObjectOutput {

    private final ObjectMapper objectMapper;
    private final PrintWriter writer;

    public JacksonObjectOutput(ObjectMapper objectMapper, OutputStream outputStream) {
        this.objectMapper = objectMapper;
        this.writer = new PrintWriter(new OutputStreamWriter(outputStream));
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        writer.write(objectMapper.writeValueAsString(obj));
        writer.println();
        writer.flush();
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        writer.println(new String(v));
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        writer.println(new String(v, off, len));
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
    }
}
