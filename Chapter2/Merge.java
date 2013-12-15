
public class Merge extends SortingAlgorithm
{

	private static void insertionSort(Comparable[] a,int lo, int hi)
	{
		
		for (int i = lo; i <= hi; i++)
		{
			for (int j = i; j > lo && less(a[j],a[j-1]);j--)
				exch(a, j, j-1);
		}
	}

	public static void merge(Comparable[] a, int lo, int mid, int hi)
	{
		for (int k = lo; k<= mid; k++)
			aux[k] = a[k];

		for (int k = hi, kk=mid+1; k > mid ; k--, kk++)
		 	aux[kk] = a[k];
		
		int i = lo, j = hi;
		for (int k = lo; k <= hi; k++) 
		{
			if (i==hi) break;
			if (less(aux[j],aux[i]))   a[k] = aux[j--];
			else					   a[k] = aux[i++];
			//Animate.drawDoubleArray(a);
 		}
	}

	private static Comparable[] aux;

	public static void sort(Comparable[] a)
	{
		aux = new Comparable[a.length];
		sort(a,0,a.length-1);
	}

	public static void sort(Comparable[] a, int lo, int hi)
	{
		if (hi <= lo) return;
		
		if (hi-lo < 15)
		{
			insertionSort(a,lo,hi);
			return;
		}

		int mid = lo + (hi-lo)/2;
		sort(a,lo,mid);
		sort(a,mid+1,hi);
		merge(a,lo,mid,hi);
	}

	public static void main(String[] args)
	{
		Double[] a = generateArrayOfDouble();
		sort(a);
		assert isSorted(a);
		show(a);
	} 
}