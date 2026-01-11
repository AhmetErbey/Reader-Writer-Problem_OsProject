/**
 * Writer - Writer thread that writes to shared data
 * 
 * Writers have exclusive access - no other readers or writers allowed.
 * Writers cannot overwrite data until all readers have read it.
 * 
 */
public class Writer implements Runnable {
    private int id;
    private SharedData sharedData;
    private ReadWriteLock lock;
    private int writeCount;
    
    public Writer(int id, SharedData sharedData, ReadWriteLock lock, int writeCount) {
        this.id = id;
        this.sharedData = sharedData;
        this.lock = lock;
        this.writeCount = writeCount;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < writeCount; i++) {
                // Small delay before attempting to write
                Thread.sleep((int)(Math.random() * 150));
                
                // Acquire write lock - exclusive access
                lock.writeLock();
                
                // Critical Section - Writing
                String newData = "Data from Writer " + id + " - Write #" + (i + 1);
                
                System.out.println("[WRITER " + id + "] *** WRITING: \"" + newData + "\" ***");
                sharedData.write(newData);
                
                // Simulate writing time
                Thread.sleep((int)(Math.random() * 100) + 20);
                
                System.out.println("[WRITER " + id + "] *** WRITE COMPLETE (Version: " + sharedData.getVersion() + ") ***");
                
                // Release write lock
                lock.writeUnLock();
                
                // Delay between writes
                Thread.sleep((int)(Math.random() * 200));
            }
            
            System.out.println("[WRITER " + id + "] Finished all writing operations.");
            
        } catch (InterruptedException e) {
            System.out.println("[WRITER " + id + "] Interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
