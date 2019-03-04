package concurrency.cookbook.recepes;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class C1R6_waiting_for_finalization_of_thread {
	public static void main(String args[]) {
		Thread dsLoaderTask = new Thread(new DataSourcesLoader());
		Thread ncLoaderTask = new Thread(new NetworkConnectionsLoader());
		
		System.out.printf("Main: initiating configuration :%s\n",new Date());
		dsLoaderTask.start();
		ncLoaderTask.start();
		
		try {
			dsLoaderTask.join();
			ncLoaderTask.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.printf("Main: Configuration has been loaded:%s\n",new Date());
	}
}

class DataSourcesLoader implements Runnable{
	public void run() {
		System.out.printf("Beginning data sources loading: %s\n", new Date());
		try {
			TimeUnit.SECONDS.sleep(4);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
class NetworkConnectionsLoader implements Runnable{
	public void run() {
		System.out.printf("Beginning network connections loading: %s\n", new Date());
		try {
			TimeUnit.SECONDS.sleep(6);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
