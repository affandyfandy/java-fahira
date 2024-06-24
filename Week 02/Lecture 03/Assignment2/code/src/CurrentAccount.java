public class CurrentAccount implements Account {
    private double balance;

    public CurrentAccount(double balance){
        this.balance = balance;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount){
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(double amount) {
        balance += amount;
        return true;
    }

    @Override
    public double queryBalance() {
        return balance;
    }
}
