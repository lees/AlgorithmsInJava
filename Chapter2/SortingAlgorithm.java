
public class SortingAlgorithm
{
	public static void sort(Comparable[] a)
	{}

	public static boolean less(Comparable[] a, int i, int j)
	{
		Animate.drawDoubleArrayLess(a,i,j);
		return a[i].compareTo(a[j]) < 0;
	}

	public static void exch(Comparable[] a, int i, int j)
	{
		Comparable t = a[i]; a[i] = a[j]; a[j] = t;
		Animate.drawDoubleArrayExchange(a,i,j);
	}

	public static void show(Comparable[] a)
	{
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i]+" ");
		StdOut.println();
	}

	public static boolean isSorted(Comparable[] a)
	{
		for (int i = 1; i < a.length; i++)
			if (less(a,i, i-1)) return false;
		return true;
	}

	public static Double[] generateArrayOfDouble()
	{
		Double[] a = new Double[100];
		for (int i=0; i < a.length ; i++) 
		{
			a[i] = Math.random();
		}
		return a;
	}

	public static void main(String[] args)
	{
		String[] a = StdIn.readStrings();
		sort(a);
		assert isSorted(a);

	} 
}