
public class Heap extends SortingAlgorithm
{

	public static void sort(Comparable[] a)
	{		
		
		int N = a.length-1;
		a[0]=0.0;
		for (int k = N/2; k>=1; k--)
			sink(a,k,N);
		while (N > 1)
		{
			exch(a,1,N--);
			sink(a,1,N);
		}

	}

	private static void sink(Comparable[] a, int k, int N)
	{
		//StdOut.printf("k=%d ; N = %d \n",k,N);
		//N = N - 1;
		while (2*k <= N)
		{
			int j = 2 * k;
			if (j < N && less(a,j,j+1)) j++;
			if (!less(a,k,j)) break;
			exch(a,k,j);
			k = j;
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