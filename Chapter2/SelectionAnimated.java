
public class SelectionAnimated
{
	public static void sort(Double[] a)
	{
		int N = a.length;
		for (int i = 0; i < N; i++)
		{
			int min = i;
			for (int j = i+1; j < N; j++)
			{
				if (less(a[j],a[min])) min = j;
			}
			exch(a,i,min);
		}
	}

	private static boolean less(Double v, Double w)
	{
		return v.compareTo(w) < 0;
	}

	private static void exch(Double[] a, int i, int j)
	{
		Double t = a[i]; a[i] = a[j]; a[j] = t;
		Animate.drawDoubleArray(a);
	}

	private static void show(Double[] a)
	{
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i]+" ");
		StdOut.println();
	}

	public static boolean isSorted(Double[] a)
	{
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}

	public static void main(String[] args)
	{
		Double[] a = new Double[100];
		for (int i=0; i < a.length ; i++) 
		{
			a[i] = Math.random();
		}

		sort(a);
		assert isSorted(a);
		show(a);
	} 
}