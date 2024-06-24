interface Account {
    boolean withdraw(double amount);
    boolean deposit(double amount);
    double queryBalance();
    
    default boolean login() {
        System.out.println("Login to the account...");
        return true;
    }
}
