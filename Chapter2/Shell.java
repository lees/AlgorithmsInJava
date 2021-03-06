
public class Shell extends SortingAlgorithm
{
	public static void sort(Comparable[] a)
	{
		int N = a.length;
		int h = 1;
		while (h < N/3) h = 3*h +1;

		while (h >= 1)
		{
			for (int i = h; i < N; i++)
			{
				for (int j = i; j >= h && less(a,j,j-h); j -= h)
					exch(a, j, j-h);
			}
			h = h/3;
		}
	}

	public static void main(String[] args)
	{
		Double[] a = generateArrayOfDouble();
		sort(a);
		assert isSorted(a);
		show(a);
	} 
}