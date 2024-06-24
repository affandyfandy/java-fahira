package lab1;

public class BankAccountDemo implements Runnable{

    private final BankAccount account;

    public BankAccountDemo(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        try {
            // Perform 10 transactions
            for (int i = 0; i < 10; i++) {
                int amount = (int) (Math.random() * 100 + 1);
                if (Math.random() > 0.5) {
                    account.withdraw(amount);
                    System.out.println(Thread.currentThread().getName() + " Withdrew " + amount);
                } else {
                    account.deposit(amount);
                    System.out.println(Thread.currentThread().getName() + " Deposited " + amount);
                }
            }
        }
        catch (InsuffientBalancesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
