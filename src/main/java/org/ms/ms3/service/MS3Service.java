package org.ms.ms3.service;

import lombok.AllArgsConstructor;
import org.ms.dto.Message;
import org.ms.ms3.feign.MS1Client;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class MS3Service {
    private final MS1Client ms1Client;

    public void forwardMessage(Message message) {
        message.setService3Timestamp(new Date());
        ms1Client.store(message);
    }
}
