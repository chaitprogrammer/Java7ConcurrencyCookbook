package concurrency.cookbook.recepes;

import java.util.Random;

/**
 * This example demonstrates the handling of uncaught exceptions in a thread group.
 * 
 * @author CG
 */
public class C1RB_Uncontrolled_Exceptions_in_ThreadGroup {
	
	public static void main(String args[]) {
		MyThreadGroup myThreadGroup = new MyThreadGroup("myThreadGroup");
		DivisionTask task = new DivisionTask();
		for(int i=0;i<2;i++) {
			Thread t = new Thread(myThreadGroup, task);
			t.start();
		}
	}

}

/*
 * This is an example of how to handle uncontrolled/runtime exceptions in thread groups.
 * 
 * JVM looks for below three before throwing an exception(writes the stack trace) and exiting the program:
 * 	1. Uncaught Exception Handler for the thread. If 1 doesn't exist JVM looks for 2.
 * 	2. uncaught exception handler for the ThreadGroup. If 2 doesn't exist JVM looks for 3.
 *  3. default uncaught exception handler.
 *  
 *  If none of the above three are present, JVM will print stack trace and exit the program.
 *  
 */
class MyThreadGroup extends ThreadGroup{
	public MyThreadGroup(String name) {
		super(name);
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.printf(" The thread %s has thrown an exception\n", t.getName());
		e.printStackTrace();
		System.out.printf("Terminating the rest of the threads\n");
		interrupt();
	}
}

class DivisionTask implements Runnable{
	/*
	 * This method runs till the random number generator generates a zero
	 *  and causes a divide by zero runtime exception.
	 */
	@Override
	public void run() {
		
		int result;
		Random random = new Random(Thread.currentThread().getId());
		while(true) {
			result = 1000/((int)(random.nextDouble()*1000));
			System.out.printf("%s : %d\n", Thread.currentThread().getId(), result);
			if(Thread.interrupted()) {
				System.out.printf("Thread %d : Inturrupted\n", Thread.currentThread().getId());
				return;
			}
		}
	}
}
