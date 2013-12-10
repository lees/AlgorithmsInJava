
public class ShellArr
{

	private static final Integer[] hs = {1,4,13,40,121,364,1093,3280,9841,29524};

	public static void sort(Comparable[] a)
	{
		int N = a.length;
		int hi = hs.length-2;
		int h = 0;
		while (hs[hi] > N) hi--;
		//hi++; 

		while (hi >= 0)
		{
			h = hs[hi];
			for (int i = h; i < N; i++)
			{
				for (int j = i; j >= h && less(a[j],a[j-h]); j -= h)
					exch(a, j, j-h);
			}
			hi--;
		}
	}

	private static boolean less(Comparable v, Comparable w)
	{
		return v.compareTo(w) < 0;
	}

	private static void exch(Comparable[] a, int i, int j)
	{
		Comparable t = a[i]; a[i] = a[j]; a[j] = t;
	}

	private static void show(Comparable[] a)
	{
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i]+" ");
		StdOut.println();
	}

	public static boolean isSorted(Comparable[] a)
	{
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}

	public static void main(String[] args)
	{
		String[] a = StdIn.readStrings();
		sort(a);
		assert isSorted(a);
		show(a);
	}
}