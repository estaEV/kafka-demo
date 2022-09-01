import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaSubscribeConsumerApp implements Runnable {
	private int id;

	public KafkaSubscribeConsumerApp (int id) {
		this.id = id;
	}

	@Override
	public void run() {
		CreateKafkaConsumer();
	}

	private void CreateKafkaConsumer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//		props.put("group.id", "test-group");

		KafkaConsumer myConsumer = new KafkaConsumer(props);
		ArrayList<String> topics = new ArrayList<>();
		topics.add("demotop2");
//		topics.add("my-other-topic-2");

		// This or that
		myConsumer.subscribe(topics);

		try {
			while (true) {
				ConsumerRecords<String, String> records = myConsumer.poll(Duration.ofSeconds(10));
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(
							String.format("Thread: %d, Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s",
									id, record.topic(), record.partition(), record.key(),  record.value()));
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			myConsumer.close();
		}
	}

}
