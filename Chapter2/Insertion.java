
public class Insertion extends SortingAlgorithm
{
	public static void sort(Comparable[] a)
	{
		int N = a.length;
		for (int i = 1; i < N; i++)
		{
			for (int j = i; j > 0 && less(a,j,j-1);j--)
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