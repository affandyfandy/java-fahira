<h2>Deadlock</h2>

Deadlock is a situation that happened in multithreaded programming where two or more threads are permanently blocked while waiting for resources that are held by each other.

### Condition for deadlock
There are 4 conditions that can occurs the deadlock
- <b>Mutual Exclusion</b>: One or more resources are being held in exclusive mode by threads. Only one thread can access the resource at a time.
- <b>Hold and Wait</b>: A thread currently holding a resource is waiting to acquire another resource that is currently held by another thread.
- <b>No Preemption</b>: Resources cannot be forcibly taken away from a thread. Threads must voluntarily release resources.
- <b>Circular Wait</b>: There exists a circular chain of threads waiting for resources from each other.

### Example
```
public static Object lock1 = new Object();
public static Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
            synchronized (lock1) {
                System.out.println("Thread 1: Acquired lock1");
                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1: Waiting for lock2");
                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock2 (shouldn't happen)");
                }
            }
        }
    });

    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("Thread 2: Acquired lock2");
                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2: Waiting for lock1");
                synchronized (lock1) {
                    System.out.println("Thread 2: Acquired lock1 (shouldn't happen)");
                }
            }
        }
    });

    thread1.start();
    thread2.start();
  }
```

#### Explanation
There are 2 objects, lock1 and lock2, are used to simulate resources. Thread 1 acquires lock1 first and then tries to acquire lock2. Thread 2 acquires lock2 first and then tries to acquire lock1. This creates a deadlock because each thread is waiting for a resource held by the other.

### Preventing Deadlock
1. <b>Resource Ordering</b>. Define a fixed order for acquiring resources. All threads must acquire resources in the same order to avoid conflicts.
2. <b>Lock Timeout</b>. Introduce a timeout mechanism for acquiring locks. If a thread cannot acquire a lock within a specific time, it gives up and tries again later.

<h2>What are noticeable things when using multiple thread?</h2>

- <b>Faster execution</b><br>
Multiple threads can significantly improve execution speed by utilizing multiple CPU cores ot work on concurrently.

- <b>Synchorization</b><br>
When multiple threads access shared resources, there's a risk of data inconsistencies if not properly synchronized. Techniques like synchronized methods or locks are needed to ensure data integrity.

- <b>Increased resouce usage</b><br>
Creating and managing threads requires additional system resources like memory and CPU overhead.

- <b>Non-deterministic behavior</b><br>
The order of thread execution and access resources can be unpredictable.

