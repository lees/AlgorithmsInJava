import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

   private Item[] a;
   private int N;


   public RandomizedQueue()                 // construct an empty randomized queue
   {
         a = (Item[]) new Object[8];
         N = 0;
   }

   public boolean isEmpty()                 // is the queue empty?
   { return N == 0; }

   public int size()                        // return the number of items on the queue
   { return N; }


   private int min(int x, int y)
   {
      if (x < y) return x;
      return y;
   }

   private void exch(int i, int j)
   {
      if (i == j) return;
      Item temp = a[i];
      a[i] = a[j];
      a[j] = temp;
   }

   private void resize(int newsize)
   {
      Item[] newa = (Item[]) new Object[newsize];
      for (int i = 0; i < min(a.length, newa.length); i++)
      {
         newa[i] = a[i];
         a[i] = null;
      }
      a = newa;
   }

   public void enqueue(Item item)           // add the item
   {
      if (item == null) 
      { 
         throw new NullPointerException("Added item can not be null"); 
      }

      if (N == a.length) resize(2 * a.length);

      a[N] = item;
      exch(StdRandom.uniform(N + 1), N);
      N++;

   }

   public Item dequeue()                    // delete and return a random item
   {
      if (N == 0)
         { throw new java.util.NoSuchElementException("You can not remove element from empty queue"); }

      N--;
      Item toreturn = a[N];
      a[N] = null;

      if (N > 0 && 4*N <= a.length) resize(a.length / 2);

      return toreturn;
   }

   public Item sample()                     // return (but do not delete) a random item
   {
      if (N == 0)
         { throw new java.util.NoSuchElementException("You can not remove element from empty queue"); }

      return a[StdRandom.uniform(N)];
   }

   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
      return new RandomizedQueueIterator();
   }

   private class RandomizedQueueIterator implements Iterator<Item>
   {
      private int current = N;
      private Item[] a_local;
      
      private RandomizedQueueIterator()
      {
         a_local = (Item[]) new Object[N];
         for (int i = 0; i < a_local.length; i++) 
            a_local[i] = a[i];

         for (int i = 1; i < a_local.length; i++)
         {
            int pos = StdRandom.uniform(i + 1);
            Item temp = a_local[pos];
            a_local[pos] = a_local[i];
            a_local[i] = temp;
         }
         
      }

      public boolean hasNext()
      {
         return current > 0;
      }

      public void remove() 
      {
         throw new UnsupportedOperationException("You can not remove items from iterator");
      }

      public Item next()
      {
         if (current == 0)
            { throw new java.util.NoSuchElementException("You can not remove element from empty queue"); }

         current--;
         Item toreturn = a_local[current];
         return toreturn;
      }
   }

   private static void rndTest(int n)
   {

      RandomizedQueue deq = new RandomizedQueue<Integer>();
      for (int i = 1; i <= 150; i++)
         deq.enqueue(i);
      
      
      for (int i = 1; i <= 25; i++)
         StdOut.println(deq.sample());

      StdOut.println("Started rnd Test");

      while (!deq.isEmpty()) StdOut.println(deq.dequeue());

      for (int i = 0; i < n; i++)
        {
            int r = StdRandom.uniform(100);
            if (r >= 90)
            {
                if (deq.isEmpty()) continue;
                StdOut.print("Removed ");
                //StdOut.println(deq.removeLast());
                StdOut.println(deq.dequeue());
            }
            else
            {
                StdOut.print("Added ");
                StdOut.println(r);
                deq.enqueue(r);
            }
        }

        while (!deq.isEmpty())
        {       
            StdOut.print("Removed ");
            StdOut.println(deq.dequeue());
         }

         StdOut.println("Started iter Test");
         StdOut.println("First iteration");

         for (int i = 0; i < 15; i++)
            deq.enqueue(i);

         Iterator<Integer> it = deq.iterator();
         while (it.hasNext())
            StdOut.println(it.next());

         StdOut.println("Second iteration");
         Iterator<Integer> it2 = deq.iterator();
         while (it2.hasNext())
            StdOut.println(it2.next());


   }

   public static void main(String[] args)   // unit testing
   {
      rndTest(300);
      
   }
}
