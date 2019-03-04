package concurrency.cookbook.recepes;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class C1R5_Sleeping_and_resuming_thread {
	
	public static void main(String args[]) {
		Thread task  = new Thread(new FileClock());
		task.start();
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		task.interrupt();
	}
	
}

class FileClock implements Runnable {
	
	public void run() {
		
//		for(int i=0; i<10; i++) 
		while(true){
			System.out.printf("%s : %s\n", new Date(), Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.out.printf("The fileClock has been interrupted : %s\n",  Thread.currentThread().getName());
				return;
			}
		}
		
	}
}
