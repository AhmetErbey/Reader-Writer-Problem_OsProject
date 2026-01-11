/**
 * SharedData - The shared resource that readers read and writers write
 * 
 * @author Student
 * @course CMP3001 Operating Systems
 */
public class SharedData {
    private String data = "Initial Data";
    private int version = 0;
    
    public void write(String newData) {
        this.data = newData;
        this.version++;
    }
    
    public String read() {
        return data;
    }
    
    public int getVersion() {
        return version;
    }
}
