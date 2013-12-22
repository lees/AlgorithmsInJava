
public class InsertionSentinel extends SortingAlgorithm
{
	public static void sort(Comparable[] a)
	{
		int N = a.length;
		int min = 0;

		for (int i = 1; i < N; i++)
		{
			if (less(a,i,min)) min = i;
		}
		exch(a,0,min);

		for (int i = 1; i < N; i++)
		{
			for (int j = i; less(a,j,j-1);j--)
				exch(a, j, j-1);
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