package org.ms.ms3;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.Tracer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ms.dto.Message;
import org.ms.ms3.kafka.KafkaMessageListener;
import org.ms.ms3.service.MS3Service;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaMessageListenerTest {
    @InjectMocks
    private KafkaMessageListener kafkaMessageListener;

    @Mock
    private MS3Service ms3Service;

    @Mock
    private Tracer tracer;

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
    public void listen() {
        doNothing().when(ms3Service).forwardMessage(any(Message.class));
        SpanBuilder spanBuilder = mock(SpanBuilder.class);
        when(spanBuilder.setAttribute(any(AttributeKey.class), any())).thenReturn(spanBuilder);
        when(spanBuilder.startSpan()).thenReturn(mock(Span.class));
        when(tracer.spanBuilder(any(String.class))).thenReturn(spanBuilder);

        kafkaMessageListener.listen(message);

        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);
        verify(ms3Service, times(1)).forwardMessage(argument.capture());
        assertThat(message, equalTo(argument.getValue()));
    }
}
