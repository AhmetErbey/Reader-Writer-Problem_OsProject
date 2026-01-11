/**
 * Test - Main class to test the Reader-Writer Problem solution
 * 
 * This class creates multiple reader and writer threads to test
 * the synchronization mechanism implemented in ReadWriteLock.
 * 
 * Test Scenarios:
 * 1. Multiple readers can read simultaneously
 * 2. Writers have exclusive access
 * 3. Readers wait while writer is writing
 * 4. Writer waits while readers are reading
 *
 */
public class Test {
    
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("  Reader-Writer Problem - Test Program");
        System.out.println("  CMP3001 Operating Systems Project");
        System.out.println("=========================================\n");
        
        // Create shared resources
        SharedData sharedData = new SharedData();
        ReadWriteLock lock = new ReadWriteLock();
        
        // Configuration
        int numReaders = 3;
        int numWriters = 2;
        int readsPerReader = 5;
        int writesPerWriter = 3;
        
        System.out.println("Configuration:");
        System.out.println("- Number of Readers: " + numReaders);
        System.out.println("- Number of Writers: " + numWriters);
        System.out.println("- Reads per Reader: " + readsPerReader);
        System.out.println("- Writes per Writer: " + writesPerWriter);
        System.out.println("\n-----------------------------------------\n");
        System.out.println("Starting threads...\n");
        
        // Create thread arrays
        Thread[] readerThreads = new Thread[numReaders];
        Thread[] writerThreads = new Thread[numWriters];
        
        // Create and start reader threads
        for (int i = 0; i < numReaders; i++) {
            Reader reader = new Reader(i + 1, sharedData, lock, readsPerReader);
            readerThreads[i] = new Thread(reader, "Reader-" + (i + 1));
            readerThreads[i].start();
        }
        
        // Create and start writer threads
        for (int i = 0; i < numWriters; i++) {
            Writer writer = new Writer(i + 1, sharedData, lock, writesPerWriter);
            writerThreads[i] = new Thread(writer, "Writer-" + (i + 1));
            writerThreads[i].start();
        }
        
        // Wait for all threads to complete
        try {
            for (Thread t : readerThreads) {
                t.join();
            }
            for (Thread t : writerThreads) {
                t.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }
        
        System.out.println("\n-----------------------------------------");
        System.out.println("All threads completed!");
        System.out.println("Final data version: " + sharedData.getVersion());
        System.out.println("Final data content: \"" + sharedData.read() + "\"");
        System.out.println("=========================================");
    }
}
