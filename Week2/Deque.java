import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> 
{
   
    private Node first;
    private Node last;
    private int size;

    private class Node
    {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque()                           // construct an empty deque
    {
        first = null;
        last = null;
        size = 0;
    }
    
    public boolean isEmpty()                 // is the deque empty?
    {
        return size == 0;
    }

    public int size()                        // return the number of items on the deque
    {
        return size;
    }

    public void addFirst(Item item)          // insert the item at the front
    {

        if (item == null) 
        { 
            throw new NullPointerException("Added item can not be null"); 
        }

        Node newnode = new Node();
        newnode.item = item;
        newnode.next = first;
        newnode.prev = null;
        size++;

        if (first == null)
        {
            first = newnode;
            last = newnode;
        }
        else
        {
            first.prev = newnode;
            first = newnode;
        }

    }

    public void addLast(Item item)           // insert the item at the end
    {

        if (item == null) 
        { 
            throw new NullPointerException("Added item can not be null"); 
        }

        Node newnode = new Node();
        newnode.item = item;
        newnode.next = null;
        newnode.prev = last;
        size++;

        if (last == null)
        {
            first = newnode;
            last = newnode;
        }
        else
        {
            last.next = newnode;
            last = newnode;
        }
    }

    public Item removeFirst()                // delete and return the item at the front
    {
        if (first == null)
            { throw new java.util.NoSuchElementException("You can not remove element from empty Deque"); }

        Item toreturn = first.item;
        first = first.next;
        if (first != null)
            first.prev = null;
        else
            last = null;
        size--;
        return toreturn;
    }

    public Item removeLast()                 // delete and return the item at the end
    {
        if (last == null)
            { throw new java.util.NoSuchElementException("You can not remove element from empty Deque"); }

        Item toreturn = last.item;
        last = last.prev;
        if (last != null) 
            last.next = null;
        else
            first = null;
        size--;
        return toreturn;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext()
        {
            return current != null;
        }

        public void remove() 
        {
            throw new UnsupportedOperationException("You can not remove items from iterator");
        }

        public Item next()
        {
            if (current == null)
            { throw new java.util.NoSuchElementException("You can not remove element from empty Deque"); }

            Item toreturn = current.item;
            current = current.next;
            return toreturn;
        }
    }


    private static void randomTest(int n)
    {
        Deque deq = new Deque<Integer>();

        int size = 0;

        for (int i = 0; i < 500; i++)
        {
            int r = StdRandom.uniform(100);
            if (r >= 90)
            {
                StdOut.print("Removed first ");
                StdOut.println(deq.removeFirst());
                size--;
            }
            else if (r >= 80) 
            {
                StdOut.print("Removed last ");
                StdOut.println(deq.removeLast());
                size--;
            }
            else if (r >= 40)
            {
                StdOut.print("Added last ");
                StdOut.println(r);
                deq.addLast(r);
                size++;
            }
            else
            {
                StdOut.print("Added first ");
                StdOut.println(r);
                deq.addFirst(r);
                size++;
            }
        }

        while (size > 0)
        {
            StdOut.print("Removed ");
            StdOut.println(deq.removeFirst());
            size--;
        }
    }

    public static void main(String[] args)   // unit testing
    {
        Deque deq = new Deque<Integer>();
        deq.addFirst(6);
        StdOut.print("Removed first ");
        StdOut.println(deq.removeFirst());
        deq.addLast(70);
        StdOut.print("Removed last ");
        StdOut.println(deq.removeFirst());

        Deque.randomTest(500);
    }
}