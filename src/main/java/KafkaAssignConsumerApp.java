import java.time.Duration;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class KafkaAssignConsumerApp implements Runnable {
	private int id;

	public KafkaAssignConsumerApp (int id) {
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

		TopicPartition myTopicPartition0 = new TopicPartition("demotop2", 0);
//		TopicPartition myTopicPartition1 = new TopicPartition("my-topic-2", 1);
//		TopicPartition myTopicPartition2 = new TopicPartition("my-topic-2", 2);

		ArrayList<TopicPartition> partitions = new ArrayList<TopicPartition>();
		partitions.add(myTopicPartition0);
//		partitions.add(myTopicPartition1);
//		partitions.add(myTopicPartition2);

		myConsumer.assign(partitions);

		try {
			while (true) {
				ConsumerRecords<String, String> records = myConsumer.poll(Duration.ofSeconds(10));
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(
							String.format("Thread: %d, Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s",
									id, record.topic(), record.partition(), record.offset(), record.key(),  record.value().toUpperCase(Locale.ROOT)));
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			myConsumer.close();
		}
	}

}
