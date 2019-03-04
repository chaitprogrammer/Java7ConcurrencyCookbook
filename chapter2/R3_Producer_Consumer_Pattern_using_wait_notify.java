package chapter2;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class R3_Producer_Consumer_Pattern_using_wait_notify {

	public static void main(String args[]) {
		EventStorage storage = new EventStorage();

		Producer producer = new Producer(storage);
		Thread producerThread = new Thread(producer);

		Consumer consumer = new Consumer(storage);
		Thread consumerThread = new Thread(consumer);

		producerThread.start();
		consumerThread.start();
		
		try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.printf("Final Size: %d\n", storage.getStorage().size());
	}

}

class EventStorage {
	private int maxSize;
	private LinkedList<Date> storage;

	
	public LinkedList<Date> getStorage() {
		return storage;
	}

	public EventStorage() {
		maxSize = 10;
		storage = new LinkedList<>();
	}

	public synchronized void set() {
		while (storage.size() == maxSize) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		storage.offer(new Date());
		System.out.printf("Set: %d\n", storage.size());
		this.notifyAll();
	}

	public synchronized void get() {
		while (storage.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.printf("Get: %d: %s\n", storage.size(), storage.poll());
		this.notifyAll();
	}
}

class Producer implements Runnable {
	private EventStorage storage;

	public Producer(EventStorage storage) {
		this.storage = storage;
	}

	@Override
	public void run() {

		for (int i = 0; i < 100; i++) {
			storage.set();
		}
	}
}

class Consumer implements Runnable {
	private EventStorage storage;

	public Consumer(EventStorage storage) {
		this.storage = storage;
	}

	@Override
	public void run() {

		for (int i = 0; i < 100; i++) {
			storage.get();
		}
	}
}
