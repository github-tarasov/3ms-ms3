package org.ms.ms3.kafka;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ms.dto.Message;
import org.ms.ms3.service.MS3Service;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaMessageListener {

    private final MS3Service ms3Service;

    private Tracer tracer;

    @KafkaListener(topics = "${kafka.topic}", groupId = "group_id")
    public void listen(@SpanAttribute Message message) {
        Span span = tracer.spanBuilder("Kafka listener")
                .setAttribute(AttributeKey.stringKey("message"), message.toString())
                .startSpan();
        log.debug("Received Message from Kafka: {}", message);
        ms3Service.forwardMessage(message);
        span.end();
    }




}
