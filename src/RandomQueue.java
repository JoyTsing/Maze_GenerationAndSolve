import java.util.LinkedList;

public class RandomQueue<E> {
    private LinkedList<E> queue;

    public RandomQueue() {
        queue = new LinkedList<>();
    }

    public void add(E e) {
        if(Math.random()<0.5)
            queue.addFirst(e);
        else
            queue.addLast(e);
    }

    public E remove() {
        if(queue.isEmpty())
            throw new IllegalArgumentException("index Error");

        if(Math.random()<0.6)
            return queue.removeFirst();
        else
            return queue.removeLast();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
