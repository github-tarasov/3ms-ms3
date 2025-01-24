package org.ms.ms3;

import io.opentelemetry.api.trace.Tracer;
import org.junit.jupiter.api.Test;
import org.ms.ms3.feign.MS1Client;
import org.ms.ms3.kafka.KafkaMessageListener;
import org.ms.ms3.service.MS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@TestPropertySource(locations = "/application-dev.properties")
class Ms3ApplicationTests {

    @Autowired
    private KafkaMessageListener kafkaMessageListener;

    @Autowired
    private MS3Service ms3Service;

    @Autowired
    private MS1Client ms1Client;

    @Autowired
    private Tracer tracer;

    @Test
    void contextLoads() {
        assertThat(kafkaMessageListener, is(notNullValue()));
        assertThat(ms3Service, is(notNullValue()));
        assertThat(ms1Client, is(notNullValue()));
        assertThat(tracer, is(notNullValue()));
    }

}
