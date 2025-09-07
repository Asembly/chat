package asembly.app.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListenerExample {

    @KafkaListener(topics = "topic1", groupId = "group1")
    public void listener(@Payload String data,
                         @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                         @Header(KafkaHeaders.OFFSET) int offset)
    {
        log.info("Received message: {}\n,In partition: {}\nWith offset: {}\n", data, partition, offset);
    }

}
