package asembly.app.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String topic)
    {
        log.info("Sending: {}", message);
        log.info("--------------------------");
        kafkaTemplate.send(topic, message);
    }

}
