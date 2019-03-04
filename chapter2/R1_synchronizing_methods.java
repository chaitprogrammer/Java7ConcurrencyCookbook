package chapter2;

public class R1_synchronizing_methods {
	
	public static void main(String args[]) {
		Account account = new Account(1000);
		
		Company company = new Company(account);
		Thread companyThread = new Thread(company);
		
		Bank bank = new Bank(account);
		Thread bankThread = new Thread(bank);
		
		System.out.printf("Account : Initial Balance: %f\n", account.getBalance());
		companyThread.start();
		bankThread.start();
		
		try {
			companyThread.join();
			bankThread.join();
			System.out.printf("Account : Final Balance: %f\n", account.getBalance());
		} catch( InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Bank implements Runnable {
	private Account account;

	public Bank(Account account) {
		this.account = account;
	}

	@Override
	public void run() {
		for(int i=0; i<100; i++) {
			account.subtractAmount(1000);
		}
	}
}

class Company implements Runnable{
	private Account account;
	
	public Company(Account account) {
		this.account = account;
	}
	
	@Override
	public void run() {
		for(int i=0; i<100; i++) {
			account.addAmount(1000);
		}
	}
}

class Account {
	public double balance;
	
	public Account(double bal) {
		this.balance = bal;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public synchronized void addAmount(double amount) {
		double temp = balance;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		temp += amount;
		balance = temp;
	}

	public synchronized void subtractAmount(double amount) {
		double temp = balance;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		temp -= amount;
		balance = temp;
	}
}
