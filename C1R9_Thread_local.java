package concurrency.cookbook.recepes;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * Thread local variables: Variables local to the thread object. This variable is local to the individual thread
 * and is not shared with other threads. 
 */
public class C1R9_Thread_local {
	
	public static void main(String args[]) {
		UnsafeTask task = new UnsafeTask();
		Thread threads[] = new Thread[10];
		for(int i=0;i<10;i++) {
			Thread thread = new Thread(task);
			thread.start();
			threads[i]=thread;
			try {
				TimeUnit.SECONDS.sleep(2);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(int i=0;i<10;i++) {
			Thread thread =threads[i];
			try {
				thread.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.printf("###############Unsafe thread execution finished\n");
		
		
		System.out.printf("############Startinng thread execution with ThreadLocal\n");
		Safetask stask = new Safetask();
		for(int i=0;i<10;i++) {
			Thread thread = new Thread(stask);
			thread.start();
			try {
				TimeUnit.SECONDS.sleep(2);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class UnsafeTask implements Runnable{
	private Date startDate;
	
	public void run() {
		startDate = new Date();
		System.out.printf("Starting Thread: %s : %s\n", Thread.currentThread().getId(),startDate);
		try {
			TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Thread Finished : %s : %s \n", Thread.currentThread().getId(), startDate);
	}
}

class Safetask implements Runnable{
	//declaring thread local variable
	private static ThreadLocal<Date> startDate = new ThreadLocal<Date>() {
		protected Date initialValue() {
			return new Date();
		}
	};
	
	public void run() {
		System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(),startDate.get());
		try {
			TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Thread Finished : %s : %s \n", Thread.currentThread().getId(), startDate.get());
	}
}
