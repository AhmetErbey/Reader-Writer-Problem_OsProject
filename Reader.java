/**
 * Reader - Reader thread that reads from shared data
 * 
 * Multiple readers can read simultaneously.
 * Readers must wait if a writer is writing.
 * Each reader reads data only once per version.
 * 
 */
public class Reader implements Runnable {
    private int id;
    private SharedData sharedData;
    private ReadWriteLock lock;
    private int readCount;
    private int lastReadVersion = -1;
    
    public Reader(int id, SharedData sharedData, ReadWriteLock lock, int readCount) {
        this.id = id;
        this.sharedData = sharedData;
        this.lock = lock;
        this.readCount = readCount;
    }
    
    @Override
    public void run() {
        try {
            // Register this reader
            lock.registerReader();
            
            for (int i = 0; i < readCount; i++) {
                // Small delay before attempting to read
                Thread.sleep((int)(Math.random() * 100));
                
                // Acquire read lock
                lock.readLock();
                
                // Critical Section - Reading
                int currentVersion = sharedData.getVersion();
                
                // Ensure we don't read the same data twice
                if (currentVersion != lastReadVersion) {
                    String data = sharedData.read();
                    System.out.println("[READER " + id + "] Reading: \"" + data + "\" (Version: " + currentVersion + ")");
                    lastReadVersion = currentVersion;
                    
                    // Simulate reading time
                    Thread.sleep((int)(Math.random() * 50) + 10);
                } else {
                    System.out.println("[READER " + id + "] Skipping - Already read version " + currentVersion);
                }
                
                // Release read lock
                lock.readUnLock();
                
                // Delay between reads
                Thread.sleep((int)(Math.random() * 100));
            }
            
            // Unregister when done
            lock.unregisterReader();
            
            System.out.println("[READER " + id + "] Finished all reading operations.");
            
        } catch (InterruptedException e) {
            System.out.println("[READER " + id + "] Interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
