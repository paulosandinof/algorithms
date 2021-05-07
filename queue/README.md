# Interface

## Java
```java
public class Queue<T> implements Iterable<T> {
	Queue();
	void enqueue(T item);
	T dequeue();
    boolean isEmpty();
	int size();
}
```