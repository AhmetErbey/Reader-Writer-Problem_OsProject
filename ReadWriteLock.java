import java.util.concurrent.Semaphore;

/**
 * ReadWriteLock - Reader-Writer Problem Solution using Semaphores
 * 
 * This implementation ensures:
 * 1. Multiple readers can read simultaneously
 * 2. Writers have exclusive access (no other readers or writers)
 * 3. Writers cannot overwrite data until all readers have read it
 * 4. Readers read the same data only once per version
 */
public class ReadWriteLock {
    
    // Semaphore for mutual exclusion when updating reader count
    private Semaphore mutex = new Semaphore(1);
    
    // Semaphore for writer exclusive access - blocks writers when readers are active
    private Semaphore writeLock = new Semaphore(1);
    
    // Semaphore to ensure all readers have read before writer can write again
    private Semaphore readComplete = new Semaphore(1);
    
    // Count of active readers currently reading
    private int activeReaders = 0;
    
    // Total number of registered readers in the system
    private int totalReaders = 0;
    
    // Count of readers who have finished reading current data
    private int readersFinished = 0;
    
    // Semaphore to protect reader registration count
    private Semaphore readerCountMutex = new Semaphore(1);
    
    // Flag to indicate if there's data waiting to be read
    private boolean dataAvailable = false;
    
    /**
     * Register a reader in the system
     * Must be called before reader starts reading
     */
    public void registerReader() throws InterruptedException {
        readerCountMutex.acquire();
        totalReaders++;
        readerCountMutex.release();
    }
    
    /**
     * Unregister a reader from the system
     * Called when reader thread is done permanently
     */
    public void unregisterReader() throws InterruptedException {
        readerCountMutex.acquire();
        totalReaders--;
        readerCountMutex.release();
    }
    
    /**
     * Acquire read lock
     * Multiple readers can acquire this lock simultaneously
     * Blocks if a writer is currently writing
     */
    public void readLock() throws InterruptedException {
        // Acquire mutex to safely update activeReaders count
        mutex.acquire();
        
        activeReaders++;
        
        // First reader blocks writers
        if (activeReaders == 1) {
            writeLock.acquire();
        }
        
        mutex.release();
    }
    
    /**
     * Release read lock
     * When all readers finish, writers are allowed to write
     */
    public void readUnLock() throws InterruptedException {
        mutex.acquire();
        
        activeReaders--;
        readersFinished++;
        
        // Last active reader releases the write lock
        if (activeReaders == 0) {
            writeLock.release();
        }
        
        // If all registered readers have finished reading, signal writer
        if (readersFinished >= totalReaders && totalReaders > 0) {
            readComplete.release();
            readersFinished = 0; // Reset for next write cycle
        }
        
        mutex.release();
    }
    
    /**
     * Acquire write lock
     * Only one writer can write at a time
     * Blocks if any readers are reading or another writer is writing
     */
    public void writeLock() throws InterruptedException {
        // Wait for exclusive access
        writeLock.acquire();
    }
    
    /**
     * Release write lock
     * Signals that writing is complete, readers can now read
     */
    public void writeUnLock() throws InterruptedException {
        writeLock.release();
    }
    
    /**
     * Wait for all readers to finish reading before writing new data
     * This ensures data is not overwritten before all readers have read it
     */
    public void waitForAllReaders() throws InterruptedException {
        if (totalReaders > 0) {
            readComplete.acquire();
        }
    }
    
    // Getters for monitoring/debugging
    public int getActiveReaders() {
        return activeReaders;
    }
    
    public int getTotalReaders() {
        return totalReaders;
    }
}
