package org.ms.ms3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ms.dto.Message;
import org.ms.ms3.feign.MS1Client;
import org.ms.ms3.service.MS3Service;

import java.util.Date;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MS3ServiceTest {
    @InjectMocks
    private MS3Service ms3Service;

    @Mock
    private MS1Client ms1Client;

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
    public void sendMessageToMS1() {
        doNothing().when(ms1Client).store(any(Message.class));

        ms3Service.forwardMessage(message);

        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);
        verify(ms1Client, times(1)).store(argument.capture());
        assertThat(message.getSessionId(), equalTo(argument.getValue().getSessionId()));
        assertThat(message.getService1Timestamp(), equalTo(argument.getValue().getService1Timestamp()));
        assertThat(message.getService2Timestamp(), equalTo(argument.getValue().getService2Timestamp()));
        assertThat(message.getService3Timestamp(), is(notNullValue()));
    }
}
