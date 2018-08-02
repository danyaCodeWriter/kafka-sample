package ru.gavrilov.sergey;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Slesarenko_Danil
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class AbstractKafkaTest {

    protected String consumerGroup = "testConsumer";

    protected KafkaTemplate<String, String> kafkaTemplate;

    protected DefaultKafkaConsumerFactory<String, String> consumerFactory;

    private boolean isInitialize = false;

    static Network network = Network.newNetwork();

    static KafkaContainer kafka = new KafkaContainer()
            .withNetwork(network)
            .withExternalZookeeper("zookeeper:2181");

    static GenericContainer zookeeper = new GenericContainer("confluentinc/cp-zookeeper:4.0.0")
            .withNetwork(network)
            .withNetworkAliases("zookeeper")
            .withEnv("ZOOKEEPER_CLIENT_PORT", "2181");

    static String url;

    static {
        Stream.of(kafka, zookeeper).parallel().forEach(GenericContainer::start);
        url = kafka.getBootstrapServers().replaceAll("PLAINTEXT://", "");
    }

    public void setUp() {
        if (isInitialize) {
            return;
        }

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(url, consumerGroup, "false");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        Map<String, Object> senderProperties = KafkaTestUtils.senderProps(url);
        senderProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        senderProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(senderProperties);

        consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);
        isInitialize = true;
    }
}
