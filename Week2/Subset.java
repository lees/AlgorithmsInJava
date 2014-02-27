
public class Subset {
   public static void main(String[] args)
   {
    int k = Integer.parseInt(args[0]);
    String[] input = StdIn.readStrings();

    RandomizedQueue<String> rq = new RandomizedQueue<String>();

    for (int i = 0; i < input.length; i++)
    {
        rq.enqueue(input[i]);
    }

    for (int i = 0; i < k; i++)
    {
        StdOut.println(rq.dequeue());
    }

   }
}