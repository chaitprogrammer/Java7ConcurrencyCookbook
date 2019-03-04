package concurrency.cookbook.recepes;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class C1R4_Controlling_inturruption_of_a_thread {
	
	public static void main(String args[]) {
		FileSearch fileSearch = new FileSearch("C:\\Tools\\", "catalina.bat");
		Thread task = new Thread(fileSearch);
		task.start();
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		task.interrupt();
	}

}

class FileSearch implements Runnable {
	private String initPath;
	private String fileName;
	
	public FileSearch(String initPath, String fileName) {
		this.initPath = initPath;
		this.fileName = fileName;
	}
	
	public void run() {
		File file = new File(initPath);
		if(file.isDirectory()) {
			try {
				directoryProcess(file);
			}catch(InterruptedException e) {
				System.out.printf("%s: the search has been inturrupted", Thread.currentThread().getName());
			}
		}
	}

	private void directoryProcess(File file) throws InterruptedException {

		File fileslist[] = file.listFiles();
		if(fileslist != null) {
			for(int i=0; i< fileslist.length;i++) {
				if(fileslist[i].isDirectory()) {
					directoryProcess(fileslist[i]);
				} else {
					fileProcess(fileslist[i]);
				}
			}
		}
		
		if (Thread.interrupted()) {
			throw new InterruptedException();
		}
	}

	private void fileProcess(File file) throws InterruptedException {

		if(file.getName().equals(fileName)) {
			System.out.printf("%s : %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
		}
		if(Thread.interrupted()) {
			throw new InterruptedException();
		}
	}
}