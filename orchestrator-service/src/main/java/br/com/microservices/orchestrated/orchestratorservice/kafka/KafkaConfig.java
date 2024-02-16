package br.com.microservices.orchestrated.orchestratorservice.kafka;

import br.com.microservices.orchestrated.orchestratorservice.TopicsEnum;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {


    public static final int REPLICA_COUNT = 1;
    public static final int PARTITION_COUNT = 1;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(KafkaConsumerConfig
                .consumerConfig(bootstrapServers, groupId, autoOffsetReset));
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(KafkaProducerConfig
                .producerConfig(bootstrapServers));
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic orchestratorTopic() {
        return buildTopic(TopicsEnum.BASE_ORCHESTRATOR.getTopic());
    }

    @Bean
    public NewTopic startSagaTopic() {
        return buildTopic(TopicsEnum.START_SAGA.getTopic());
    }
    @Bean
    public NewTopic finishFailTopic() {
        return buildTopic(TopicsEnum.FINISH_FAIL.getTopic());
    }
    @Bean
    public NewTopic FinishSuccessTopic() {
        return buildTopic(TopicsEnum.FINISH_SUCCESS.getTopic());
    }

    @Bean
    public NewTopic notifyEndingTopic() {
        return buildTopic(TopicsEnum.NOTIFY_ENDING.getTopic());
    }

    /*
    Create all public NewTopic from TopicsENum
     */
    @Bean
    public NewTopic productValidationSuccessTopic() {
        return buildTopic(TopicsEnum.PRODUCT_VALIDATION_SUCCESS.getTopic());
    }

    @Bean
    public NewTopic productValidationFailTopic() {
        return buildTopic(TopicsEnum.PRODUCT_VALIDATION_FAIL.getTopic());
    }

    @Bean
    public NewTopic inventorySuccessTopic() {
        return buildTopic(TopicsEnum.INVENTORY_SUCCESS.getTopic());
    }

    @Bean
    public NewTopic inventoryFailTopic() {
        return buildTopic(TopicsEnum.INVENTORY_FAIL.getTopic());
    }

    @Bean
    public NewTopic paymentSuccessTopic() {
        return buildTopic(TopicsEnum.PAYMENT_SUCCESS.getTopic());
    }

    @Bean
    public NewTopic paymentFailTopic() {
        return buildTopic(TopicsEnum.PAYMENT_FAIL.getTopic());
    }

    private NewTopic buildTopic(String name) {
        return TopicBuilder
                .name(name)
                .replicas(REPLICA_COUNT)
                .partitions(PARTITION_COUNT)
                .build();
    }

    private static class KafkaConsumerConfig {
        private static Map<String, Object> consumerConfig(final String bootstrapServers,
                                                          final String groupId,
                                                          final String autoOffsetReset) {
            var props = new HashMap<String, Object>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

            return props;
        }

    }

    private static class KafkaProducerConfig {
        private static Map<String, Object> producerConfig(final String bootstrapServers) {
            var props = new HashMap<String, Object>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringDeserializer.class);

            return props;
        }

    }
}