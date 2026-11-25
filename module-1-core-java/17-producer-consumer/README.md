# Assignment 17 - Producer-Consumer using Thread Synchronization

## What it does
Producer threads put items into a limited-size buffer and consumer threads take them out. The buffer uses wait() and notifyAll(), so producers wait when it is full and consumers wait when it is empty. A simulation checks that nothing is lost, nothing is duplicated and order is kept.

## Features
    Simulation: 1-10 producers, 1-10 consumers, 1-50 items each, buffer size 1-20
    Blocking demo: shows a producer and a consumer in the WAITING state
    Shutdown demo: close() ends the work cleanly

## Concepts
- Producer-consumer problem and the bounded buffer
- synchronized, wait() and notifyAll()
- Why wait() is used inside a while loop
- Clean shutdown with a closed flag (no endless waiting)
- AtomicInteger, AtomicLong and ConcurrentHashMap for counting across threads

## Files
    src/prodcons/BoundedBuffer.java         -> the shared buffer (wait/notifyAll)
    src/prodcons/ProducerConsumerApp.java   -> the menu, simulation and demos

## Run
    cd 17-producer-consumer
    javac -d out src\prodcons\*.java
    java -cp out prodcons.ProducerConsumerApp

## Sample test
    Type: 1, then 3 producers, 3 consumers, 20 items, buffer size 1
    Result: Produced 60, Consumed 60, Duplicates 0, Check PASSED

    Type: 2
    Result: producer and consumer threads shown as WAITING, then released

    Type: 3
    Result: put after close is rejected, waiting consumer wakes up

## What happens inside
1. Each producer thread calls put(), which waits while the buffer is full.
2. Each consumer thread calls take(), which waits while the buffer is empty.
3. When all producers are done, the buffer is closed and consumers finish what is left.
4. The simulation compares counts, a checksum, ordering and the buffer size.