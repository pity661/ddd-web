package com.wenky.commons.dubbo.spi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TimeZone;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-02-06 17:37
 */
public class JacksonSerialization implements Serialization {

    private static ObjectMapper objectMapper =
            new ObjectMapper() {
                {
                    disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    setTimeZone(TimeZone.getDefault());
                    // java.time.LocalDateTime config
                    registerModule(new ParameterNamesModule());
                    registerModule(new Jdk8Module());
                    registerModule(new JavaTimeModule());
                    // java.time.LocalDateTime config
                }
            };

    public static synchronized void setObjectMapper(ObjectMapper objectMapper) {
        JacksonSerialization.objectMapper = objectMapper;
    }

    @Override
    public byte getContentTypeId() {
        return 28;
    }

    @Override
    public String getContentType() {
        return "text/json";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return new JacksonObjectOutput(objectMapper, output);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream input) throws IOException {
        return new JacksonObjectInput(objectMapper, input);
    }
}
