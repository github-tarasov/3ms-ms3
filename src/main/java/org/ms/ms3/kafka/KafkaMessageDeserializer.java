package org.ms.ms3.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.ms.dto.Message;

import java.util.Map;

@Slf4j
public class KafkaMessageDeserializer implements Deserializer<Message> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Message deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.debug("Null received at deserializing");
                return null;
            }
            log.debug("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), Message.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to Message");
        }
    }

    @Override
    public void close() {
    }
}


