package com.wenky.provider.framework.disruptor.factory;

import com.lmax.disruptor.EventFactory;
import com.wenky.provider.framework.disruptor.model.MessageModel;
import lombok.SneakyThrows;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-12 15:55
 */
public class CustomEventFactory implements EventFactory<MessageModel> {
    @SneakyThrows
    @Override
    public MessageModel newInstance() {
        return MessageModel.class.newInstance();
    }
}
