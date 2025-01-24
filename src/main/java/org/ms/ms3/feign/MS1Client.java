package org.ms.ms3.feign;

import org.ms.dto.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="feign-ms1", url="${feign-ms1.url}")
public interface MS1Client {
    @PostMapping("/store")
    void store(@RequestBody Message message);
}
