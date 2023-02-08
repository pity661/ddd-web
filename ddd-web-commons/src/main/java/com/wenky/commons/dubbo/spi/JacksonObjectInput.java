package com.wenky.commons.dubbo.spi;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.*;
import java.lang.reflect.Type;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.serialize.ObjectInput;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-02-06 18:41
 */
public class JacksonObjectInput implements ObjectInput {
    private final ObjectMapper objectMapper;

    private final BufferedReader reader;

    public JacksonObjectInput(ObjectMapper objectMapper, InputStream input) {
        this.objectMapper = objectMapper;
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        return objectMapper.readTree(readLine());
    }

    @Override
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return read(cls);
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        JavaType javaType = TypeFactory.defaultInstance().constructType(type);
        return objectMapper.readValue(readLine(), javaType);
    }

    @Override
    public boolean readBool() throws IOException {
        return read(boolean.class);
    }

    @Override
    public byte readByte() throws IOException {
        return read(byte.class);
    }

    @Override
    public short readShort() throws IOException {
        return read(short.class);
    }

    @Override
    public int readInt() throws IOException {
        return read(int.class);
    }

    @Override
    public long readLong() throws IOException {
        return read(long.class);
    }

    @Override
    public float readFloat() throws IOException {
        return read(float.class);
    }

    @Override
    public double readDouble() throws IOException {
        return read(double.class);
    }

    @Override
    public String readUTF() throws IOException {
        return read(String.class);
    }

    @Override
    public byte[] readBytes() throws IOException {
        return readLine().getBytes();
    }

    private String readLine() throws IOException {
        String line = reader.readLine();
        if (StringUtils.isBlank(line)) {
            throw new EOFException();
        }
        return line;
    }

    private <T> T read(Class<T> tClass) throws IOException {
        String json = readLine();
        return objectMapper.readValue(json, tClass);
    }
}
