public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Create new current account...");
        CurrentAccount currentAccount = new CurrentAccount(12000000);
        currentAccount.login();
        System.out.println("Deposit 5000000 on current account...");
        currentAccount.deposit(5000000);
        String balance = String.format("%.0f", currentAccount.queryBalance());
        System.out.println("Balance: " + balance);

        System.out.println("Create new saving account...");
        SavingAccount savingAccount = new SavingAccount(200000000);
        savingAccount.login();
        System.out.println("Withdraw 12300000 from saving account...");
        savingAccount.withdraw(12300000);
        String currBalance = String.format("%.0f", savingAccount.queryBalance());
        System.out.println("Balance: " + currBalance);
    }
}
