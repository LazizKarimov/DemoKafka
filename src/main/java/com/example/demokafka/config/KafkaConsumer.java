    package com.example.demokafka.config;

    import com.example.demokafka.dto.User;
    import org.apache.kafka.clients.consumer.ConsumerConfig;
    import org.apache.kafka.common.serialization.StringDeserializer;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.kafka.annotation.EnableKafka;
    import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
    import org.springframework.kafka.config.KafkaListenerContainerFactory;
    import org.springframework.kafka.core.ConsumerFactory;
    import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
    import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
    import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

    import java.util.HashMap;
    import java.util.Map;

    @Configuration
    @EnableKafka
    public class KafkaConsumer {

        @Bean
        public ConsumerFactory<String, String> consumerFactory() {
            Map<String, Object> consumerProps = new HashMap<>();

            consumerProps.put(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                    "localhost:9092"
            );

            consumerProps.put(
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                    StringDeserializer.class
            );

            consumerProps.put(
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                    StringDeserializer.class
            );

            return new DefaultKafkaConsumerFactory<>(consumerProps);
        }

        /**
         * Все сообщения, удовлетворяющие критериям фильтра, будут отброшены еще до того,
         * как они попадут к слушателю (listener). Здесь отбрасываются сообщения, содержащие слово "ignored".
         */
        @Bean
        public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, String> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            factory.setRecordFilterStrategy(record -> record.value().contains("ignored"));
            return factory;
        }

        @Bean
        public ConsumerFactory<String, User> userConsumerFactory() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JacksonJsonDeserializer<>(User.class));
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(userConsumerFactory());
            return factory;
        }
    }
