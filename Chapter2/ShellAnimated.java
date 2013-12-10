
public class ShellAnimated
{
	public static void sort(Double[] a)
	{
		int N = a.length;
		int h = 1;
		while (h < N/3) h = 3*h +1;

		while (h >= 1)
		{
			for (int i = h; i < N; i++)
			{
				for (int j = i; j >= h && less(a[j],a[j-h]); j -= h)
					exch(a, j, j-h);
			}
			h = h/3;
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