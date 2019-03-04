package concurrency.cookbook.recepes;

import java.lang.Thread.UncaughtExceptionHandler;

public class C1R8_uncontrolled_exceptions_in_threads {
	
	// unchecked exceptions causes stack trace and ends the task. To continue with the task
	// java provides a way to handle unchecked exceptions raised in thread class.
	
	public static void main(String arfs[]) {
		Task task = new Task();
		Thread t = new Thread(task);
		t.setUncaughtExceptionHandler(new ExceptionHandler());// this will handle all unchecked exceptions 
		t.start();
	}
}

class ExceptionHandler implements UncaughtExceptionHandler{
	
	public void uncaughtException(Thread t, Throwable e) {
		System.out.printf("An exception has been captured\n");
		System.out.printf("Thread: %s\n", t.getId());
		System.out.printf("Exception: %s: %s\n", e.getClass().getName(), e.getMessage());
		System.out.printf("Stack Trace: \n");
		e.printStackTrace(System.out);
		System.out.printf("Thread status: %s\n", t.getState());
	}
}

class Task implements Runnable{
	public void run() {
		int numero = 1/0;
	}
}
