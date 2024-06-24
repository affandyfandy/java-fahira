package lab1;

public class BankAccount {
    double balance;

    public BankAccount(double balance){
        this.balance = balance;
    }

    public synchronized void withdraw(double amount){
        try{
            balance -= amount;
        } catch (InsuffientBalancesException e){
            throw new InsuffientBalancesException("Insuffienct balance!");
        }
    }

    public synchronized void deposit(double amount){
        try{
            balance += amount;
        } catch (InsuffientBalancesException e){
            throw new InsuffientBalancesException("Insuffienct balance!");
        }
    }
}


class InsuffientBalancesException extends RuntimeException{

    public InsuffientBalancesException(String message){
        super(message);
    }
}