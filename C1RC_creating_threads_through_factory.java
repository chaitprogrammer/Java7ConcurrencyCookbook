package concurrency.cookbook.recepes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Use ThreadFactory interface to generate threads from a factory using factory pattern.
 * 
 * @author CG
 */
public class C1RC_creating_threads_through_factory {

	public static void main(String[] args) {

		MyThreadFatory myThreadFatory = new MyThreadFatory("MyThreadFatory");
		
		System.out.printf("Starting thread creation\n");
		
		for(int i=0;i<10;i++) {
			Thread t = myThreadFatory.newThread(new FTask());
			t.start();
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.printf("Factory stats:\n");
		System.out.printf("%s\n", myThreadFatory.getStats());
		
		System.out.printf("Stats completed");
	}
}

class MyThreadFatory implements ThreadFactory{
	private int counter;
	private String name;
	private List<String> stats;
	
	public MyThreadFatory(String name) {
		this.counter = 0;
		this.name = name;
		this.stats = new ArrayList<>();
	}
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, name+"-Thread_"+counter);
		counter++;
		stats.add(String.format("Created thread %d with name %s on %s\n", t.getId(), t.getName(), new Date()));
		return t;
	}
	
	public String getStats() {
		StringBuffer stringBuffer = new StringBuffer();
		Iterator<String> iterator = stats.iterator();
		while(iterator.hasNext()) {
			stringBuffer.append(iterator.next());
			stringBuffer.append("\n");
		}
			
		return stringBuffer.toString();
	}
}

class FTask implements Runnable{
	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}