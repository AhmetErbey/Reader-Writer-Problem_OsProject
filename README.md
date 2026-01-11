# Reader-Writer Problem - Synchronization Project

**Course:** CMP3001 Operating Systems  
**Topic:** Reader-Writer Problem using Semaphores

## 📖 Description

This project implements a solution to the classic Reader-Writer synchronization problem using Java Semaphores. The solution ensures:

- Multiple readers can read simultaneously
- Writers have exclusive access (no other readers or writers)
- No race conditions or data corruption

## 🛠️ Requirements

- Java JDK 8 or higher

## 🚀 How to Run

### Step 1: Compile
```bash
javac *.java
```


### Step 2: Run Main Test
```bash
java Test
```

## 📁 Files

| File | Description |
|------|-------------|
| `ReadWriteLock.java` | Main synchronization solution (with all requirements) |
| `Test.java` | Main test program |
| `Reader.java` | Reader thread class |
| `Writer.java` | Writer thread class |
| `SharedData.java` | Shared data class |

## 🔑 Solution Algorithm


```
Semaphores:
- mutex (1): Protects activeReaders and readersFinished
- writeLock (1): Writer's exclusive access
- readComplete (1): Ensures all readers have read before next write
- readerCountMutex (1): Protects totalReaders

readLock():
  mutex.acquire()
  activeReaders++
  if (activeReaders == 1) writeLock.acquire()  // First reader blocks writers
  mutex.release()

readUnLock():
  mutex.acquire()
  activeReaders--
  readersFinished++
  if (activeReaders == 0) writeLock.release()  // Last reader allows writers
  if (readersFinished >= totalReaders && totalReaders > 0) {
    readComplete.release()
    readersFinished = 0
  }
  mutex.release()

writeLock():
  writeLock.acquire()  // Exclusive access

writeUnLock():
  writeLock.release()

waitForAllReaders():
  if (totalReaders > 0) readComplete.acquire()
```

## 📊 Expected Output

```
>>> [Writer 1] WRITING: "Written by Writer 1 (v1)" <<<
  [Reader 1] READING: "Written by Writer 1 (v1)"
  [Reader 2] READING: "Written by Writer 1 (v1)"
  [Reader 3] READING: "Written by Writer 1 (v1)"
>>> [Writer 2] WRITING: "Written by Writer 2 (v2)" <<<
...
```

## ✅ Verification

- Writers never write simultaneously
- Multiple readers can read at the same time
- No reading during writing
- No writing during reading

## 👤 Author

Student - CMP3001 Operating Systems
