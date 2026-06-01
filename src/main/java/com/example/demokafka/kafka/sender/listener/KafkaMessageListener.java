package com.example.demokafka.kafka.sender.listener;

import com.example.demokafka.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMessageListener {

    @KafkaListener(topics = "topic-1", groupId = "group-1")
    void listener(@Payload String data,
                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                  @Header(KafkaHeaders.OFFSET) long offset // номер сообщения
    ) {
        log.info("Received message [{}] from group1, partition-{} with offset-{}",
                data,
                partition,
                offset);
    }

    @KafkaListener(groupId = "group2",
            topicPartitions = @TopicPartition(topic = "topic-2",
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0"),
                            @PartitionOffset(partition = "3", initialOffset = "0")}))
    public void listenToPartition(@Payload String message,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received Message [{}] from partition-{}",
                message,
                partition);
    }

    /**
     * containerFactory — это ссылка на бин, который создает и настраивает контейнер слушателя
     */
    @KafkaListener(topics = "topic-3", groupId = "user-group",
            containerFactory = "userKafkaListenerContainerFactory")
    public void listenerWithMessageConverter(User user) {
        log.info("Received message through MessageConverterUserListener [{}]", user);
    }
}
