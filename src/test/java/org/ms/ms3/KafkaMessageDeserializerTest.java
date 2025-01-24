package org.ms.ms3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ms.dto.Message;
import org.ms.ms3.kafka.KafkaMessageDeserializer;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KafkaMessageDeserializerTest {

    private final KafkaMessageDeserializer kafkaMessageDeserializer = new KafkaMessageDeserializer();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Message message;

    @BeforeEach
    public void beforeEach() {
        message = Message.builder()
                .sessionId(123)
                .service1Timestamp(new Date())
                .service2Timestamp(new Date())
                .build();
    }

    @Test
    public void deserialize_Null() {
        Message result = kafkaMessageDeserializer.deserialize("ms3", null);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void deserialize_NotNull() throws JsonProcessingException {
        byte[] data = objectMapper.writeValueAsBytes(message);
        Message result = kafkaMessageDeserializer.deserialize("ms3", data);

        assertThat(result, equalTo(message));
    }

    @Test
    public void deserialize_Exception() {
        Exception exception = assertThrows(SerializationException.class, () -> {
            byte[] data = new byte[] {1,2,3};
            kafkaMessageDeserializer.deserialize("ms3", data);
        });

        assertThat(exception.getClass(), equalTo(SerializationException.class));
    }

}
