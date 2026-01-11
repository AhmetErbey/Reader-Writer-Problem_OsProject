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

### Step 2: Run Simple Test
```bash
java TestSimple
```

### Step 3: Run Detailed Test (Optional)
```bash
java Test
```

## 📁 Files

| File | Description |
|------|-------------|
| `ReadWriteLockSimple.java` | Main solution - 4 required methods |
| `ReadWriteLock.java` | Extended version with additional features |
| `TestSimple.java` | Simple test with inline thread classes |
| `Test.java` | Detailed test program |
| `Reader.java` | Reader thread class |
| `Writer.java` | Writer thread class |
| `SharedData.java` | Shared data class |

## 🔑 Solution Algorithm

```
Semaphores:
- mutex (1): Protects readCount variable
- wrt (1): Writer's exclusive access

readLock():
  mutex.acquire()
  readCount++
  if (readCount == 1) wrt.acquire()  // First reader blocks writers
  mutex.release()

readUnLock():
  mutex.acquire()
  readCount--
  if (readCount == 0) wrt.release()  // Last reader allows writers
  mutex.release()

writeLock():
  wrt.acquire()  // Exclusive access

writeUnLock():
  wrt.release()
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
