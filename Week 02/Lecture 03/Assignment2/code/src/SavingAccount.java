public class SavingAccount implements Account {

    private double balance;

    public SavingAccount(double balance){
        this.balance = balance;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double queryBalance() {
        return balance;
    }
    
}