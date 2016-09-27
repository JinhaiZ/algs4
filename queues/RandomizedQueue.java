import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;
    
    public RandomizedQueue() {
        this.array = (Item[]) new Object[2];
        this.size = 0;
    }                
    // construct an empty randomized queue
        
    public boolean isEmpty() {
        return (this.size == 0);
    }               
    // is the queue empty?
        
    public int size() {
        return this.size;
    }                       
    // return the number of items on the queue
    
    private void resize(int length) {
        Item[] temp = (Item[]) new Object[length];
        for (int i = 0; i < this.size; i++) {
            temp[i] = this.array[i];
        }
        this.array = temp;
    }
    
    public void enqueue(Item item) {
        if (item == null) {
            throw new  java.lang.NullPointerException("add null");
        }
        if (this.size == array.length) resize(this.array.length*2);
        
        this.array[this.size++] = item;
    }           
    // add the item
        
    public Item dequeue() {
        if (this.size < 1) {
            throw new java.util.NoSuchElementException("empty");
        }
        int random = StdRandom.uniform(this.size);
        Item removed = this.array[random];
        if (random == this.size - 1) {
            this.size -= 1;
        } else {
            this.array[random] = this.array[this.size-1];
            this.size -= 1;
        }
        this.array[this.size] = null;
        if ( this.size >0 && this.size == this.array.length/4 ) {
            resize(this.array.length/2);
        }
        return removed;
    }
    // remove and return a random item
        
    public Item sample() {
        if (this.size < 1) {
            throw new java.util.NoSuchElementException("empty");
        }
        int random = StdRandom.uniform(this.size);
        Item removed = this.array[random];
        return removed;    
    }                     
    // return (but do not remove) a random item
        
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }        
    // return an independent iterator over items in random order
    private class RandomIterator implements Iterator<Item> {
        private int current = 0;
        private int[] ind = new int[size];
        {
        for(int i = 0;i < ind.length; i++) {
            ind[i] = i;
        }
        StdRandom.shuffle(ind);
        }
        public boolean hasNext() {
            return (current < ind.length);
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("iterator rm");
        }
        
        public Item next() {
            if (current < ind.length) {
                Item item = array[ind[current++]];
                return item;
            } else {
                throw new java.util.NoSuchElementException("no more item");
            }
        }
    }    
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(4);
        test.enqueue(5);
        test.enqueue(6);
        Iterator<Integer> i = test.iterator();
        while(i.hasNext()) {
            StdOut.println(i.next());
        } 
    }  
   // unit testing
}