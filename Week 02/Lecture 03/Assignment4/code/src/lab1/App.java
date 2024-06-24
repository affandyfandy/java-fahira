package lab1;

public class App {
    private static final int NUM_THREADS = 4;
    
    public static void main(String[] args) {
        BankAccount account = new BankAccount(100);
        // Set num of threads
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(new BankAccountDemo(account));
            thread.start();
        }
    }
}
