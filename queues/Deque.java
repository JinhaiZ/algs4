import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;
    
    public class Node {
        private Item data;
        private Node next;
        private Node prev;
        public Node(Item data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
        // construct an empty deque
    public boolean isEmpty() {
        return (this.size == 0);
    }
        // is the deque empty?
    public int size() {
        return this.size;
    }   
        // return the number of items on the deque
    public void addFirst(Item item) {
        checkAddNew(item);
        
        if (this.first == null) {
            this.first = new Node(item);
            this.last = this.first;
        } else {
            Node newFirst = new Node(item);
            newFirst.next = this.first;
            this.first.prev = newFirst;
            this.first = newFirst;
        }
        
        this.size += 1;
    }
        // add the item to the front
    public void addLast(Item item) {
        checkAddNew(item);
        
        if (this.first == null) {
            this.first = new Node(item);
            this.last = this.first;
        } else {
            Node newLast = new Node(item);
            this.last.next = newLast;
            newLast.prev = this.last;
            this.last = newLast;
        }
        
        this.size += 1;
    }
        // add the item to the end
    public Item removeFirst() {
        checkRemove(this.size);
        
        Item removed = this.first.data;
        if (this.size == 1) {
            this.first = null;
            this.last = this.first;
        } else {
            Node newFirst = this.first.next;
            newFirst.prev = null;
            this.first = newFirst;
        }
        
        this.size -= 1;
        return removed;
    }
        // remove and return the item from the front
    public Item removeLast() {
        checkRemove(this.size);
        
        Item removed = this.last.data;
        if (this.size == 1) {
            this.last = null;
            this.first = this.last;
        } else {
            Node newLast = this.last.prev;
            newLast.next = null;
            this.last = newLast;
        }
        
        this.size -= 1;
        return removed;
    }
        
        // remove and return the item from the end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException("iterator rm");
        }
        public Item next() {
            if (current != null) {
                Item item = current.data;
                current = current.next;
                return item;
            } else {
                throw new java.util.NoSuchElementException("no more item");
            }
        }
    }
        // return an iterator over items in order from front to end
    private void checkAddNew(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("attemps to add null"); 
        }
    }
    private void checkRemove(int s) {
        if (s < 1) {
            throw new java.util.NoSuchElementException("empty deque");
        }
    }
    public static void main(String[] args) {
        // unit testing
        Deque<Integer> test = new Deque<Integer>();
        test.addFirst(1);
        test.addLast(1);
        test.addFirst(2);
        test.addLast(2);
        test.addFirst(3);
        test.addLast(3);
        test.addFirst(4);
        test.addLast(4);
        Iterator<Integer> i = test.iterator();
        while (i.hasNext()) {
            StdOut.println(i.next());
        }
    }
}