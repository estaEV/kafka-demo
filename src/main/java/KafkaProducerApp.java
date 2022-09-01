import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerApp implements Runnable {

	@Override
	public void run() {
		CreateKafkaProducer();
	}

	private static void CreateKafkaProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		KafkaProducer<String, String> myProducer = new KafkaProducer<String, String>(props);
		try {
			for (int i = 0; i < 150; i++) {
				ProducerRecord<String, String> myRecord = new ProducerRecord<String, String>("demotop2", Integer.toString(i), "MyMessage: " + Integer.toString(i));
				myProducer.send(myRecord);
				System.out.println(myRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			myProducer.close();
		}
	}
}
