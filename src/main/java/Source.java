import java.sql.SQLOutput;

public class Source {
	public static void main(String[] args) {
		Thread producerThread = new Thread(new KafkaProducerApp());
		producerThread.start();

//		Assign consumer
		Thread assignConsumerThread = new Thread(new KafkaAssignConsumerApp(0));
		assignConsumerThread.start();

//		Subscribe consumer
//		Thread subscribeConsumerThread0 = new Thread(new KafkaSubscribeConsumerApp(0));
//		subscribeConsumerThread0.start();

	}

}
